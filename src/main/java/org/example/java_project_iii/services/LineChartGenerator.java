package org.example.java_project_iii.services;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionsTable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LineChartGenerator {

    private static LineChartGenerator instance; // Singleton instance
    private Database db;

    // Private constructor to prevent instantiation
    private LineChartGenerator() throws Exception {
        this.db = Database.getInstance();
    }

    // Method to get the single instance of the class
    public static LineChartGenerator getInstance() throws Exception {
        if (instance == null) {
            instance = new LineChartGenerator();
        }
        return instance;
    }

    // Method to create a line chart that shows progress towards a budget goal
    public LineChart<Number, Number> createGoalProgressChart(int budgetId) throws Exception {
        // Fetch budget details (goal amount, start date, end date)
        BudgetPOJO budget = BudgetTable.getInstance().getBudget(budgetId);
        if (budget == null) {
            throw new Exception("Budget not found.");
        }

        double goalAmount = budget.getGoal_amount();
        Date startDate = (Date) budget.getStart_date();
        Date endDate = (Date) budget.getEnd_date();

        // Convert start date and end date to LocalDate
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();

        // Create the X and Y axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Days since Start");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount Spent");

        // Create the LineChart with the defined axes
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Budget Goal Progress");

        // Create a series to represent the progress over time
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Progress");

        // Fetch transactions filtered by the budget ID, start date, and end date
        List<TransactionsPOJO> transactions = fetchTransactionsForBudget(budgetId, startDate, endDate);

        // Calculate the progress for each transaction
        double cumulativeSpending = 0;
        for (TransactionsPOJO transaction : transactions) {
            LocalDate transactionDate = transaction.getTransaction_date().toLocalDate(); // Convert java.sql.Date to LocalDate
            cumulativeSpending += transaction.getAmount();
            double progressPercentage = (cumulativeSpending / goalAmount) * 100;

            // Convert transaction date to the number of days since start date
            long daysSinceStart = ChronoUnit.DAYS.between(startLocalDate, transactionDate);

            // Add data to the chart (use days since start date as X and cumulative spending as Y)
            series.getData().add(new XYChart.Data<>(daysSinceStart, progressPercentage));
        }

        // Add the series to the chart
        lineChart.getData().add(series);

        return lineChart;
    }

    private List<TransactionsPOJO> fetchTransactionsForBudget(int budgetId, Date startDate, Date endDate) throws SQLException {
        List<TransactionsPOJO> transactions = new ArrayList<>();

        // SQL query to fetch transactions within the given date range for the specific budgetId
        String sql = "SELECT * FROM transactions WHERE transaction_id = ? AND transaction_date BETWEEN ? AND ?";

        try (

                PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create and populate a TransactionsPOJO object for each row in the result set
                    TransactionsPOJO transaction = new TransactionsPOJO();
                    transaction.setTransaction_type_id(rs.getInt("transaction_id"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setTransaction_date(rs.getDate("transaction_date"));
                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }

    // Helper method to calculate the percentage of goal achieved
    public double calculateProgressPercentage(double cumulativeSpending, double goalAmount) {
        return (cumulativeSpending / goalAmount) * 100;
    }
}
