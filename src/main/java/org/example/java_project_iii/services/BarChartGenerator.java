package org.example.java_project_iii.services;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.tables.BudgetTable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Singleton class for generating bar charts to track budget goals
 */
public class BarChartGenerator {
    private static BarChartGenerator instance;
    private Database db;

    // Constructor
    private BarChartGenerator() throws Exception {
        this.db = Database.getInstance();
    }

    /**
     * Get the singleton instance of BarChartGenerator
     * @return the single instance of this class
     * @throws Exception if any error occur
     */
    public static BarChartGenerator getInstance() throws Exception {
        if (instance == null) {
            synchronized (BarChartGenerator.class) {
                if (instance == null) {
                    instance = new BarChartGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * Create a bar chart
     * @param budgetId unique id for each row
     * @return barchart
     * @throws Exception if there is any error
     */
    public BarChart<String, Number> createGoalProgressBarChart(int budgetId) throws Exception {

        // Fetch budget details (goal amount, start date, end date)
        BudgetPOJO budget = BudgetTable.getInstance().getBudget(budgetId);
        if (budget == null) {
            System.out.println("Budget not found.");
            return createPlaceholderChart("Please set/select your budget goal.");
        }

        double goalAmount = budget.getGoal_amount();
        Date startDate = (Date) budget.getStart_date();
        Date endDate = (Date) budget.getEnd_date();

        // Convert start and end dates to LocalDate (date without time information)
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate endLocalDate = endDate.toLocalDate();

        // Create the X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Days since Start");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Progress (%)");

        // Disable auto-ranging, set the lower and upper bound for the y-axis
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        // Create the BarChart with the defined axes
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Budget Goal Progress");

        /**
         * Create a series to represent the progress
         * XYChart is a base class for all charts that use an XY coordinate system
         * String as the x-axis values and Number as the y-axis values.
         */
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //displayed as a label on the chart
        series.setName("Progress");

        // Fetch transactions filtered by the budget ID, start date, and end date
        List<TransactionsPOJO> transactions = fetchTransactionsForBudget(budgetId, startDate, endDate);

        // Calculate the progress for each transaction
        double cumulativeSpending = 0;
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for the given budget ID and date range.");
        } else {
            System.out.println("Transactions fetched: " + transactions.size());
        }

        for (TransactionsPOJO transaction : transactions) {
            LocalDate transactionDate = transaction.getTransaction_date().toLocalDate();
            cumulativeSpending += transaction.getAmount();
            double progressPercentage = (cumulativeSpending / goalAmount) * 100;

            // Convert transaction date to the number of days since start date
            long daysSinceStart = ChronoUnit.DAYS.between(startLocalDate, transactionDate);

            System.out.println("Transaction Date: " + transactionDate + ", Days since Start: " + daysSinceStart + ", Progress: " + progressPercentage);

            // Add data to the chart (use days since start date as X and cumulative spending as Y)
            series.getData().add(new XYChart.Data<>(String.valueOf(daysSinceStart), progressPercentage));
        }

        // Add the series to the chart
        barChart.getData().add(series);

        return barChart;
    }

    /**
     * Create a placeholder bar chart to display a message when no data is available
     * @param message the message to display on the chart
     * @return a bar chart with the message as its title
     */
    private BarChart<String, Number> createPlaceholderChart(String message) {
        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create the bar chart
        BarChart<String, Number> placeholderChart = new BarChart<>(xAxis, yAxis);
        placeholderChart.setTitle(message); // Set the message as the title
        placeholderChart.setLegendVisible(false); // Hide legend since no data exists

        return placeholderChart;
    }


    private List<TransactionsPOJO> fetchTransactionsForBudget(int budgetId, Date startDate, Date endDate) throws SQLException {
        List<TransactionsPOJO> transactions = new ArrayList<>();

        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);

        // SQL query to fetch transactions directly with the given parameters
        String sql = "SELECT t.transaction_id, t.amount, t.transaction_date " +
                "FROM transactions t " +
                "JOIN transaction_types tt ON t.transaction_type_id = tt.transaction_type_id " +
                "JOIN budgets b ON b.transaction_id = tt.transaction_type_id " +
                "WHERE b.budget_id = " + budgetId + " " +
                "AND t.transaction_date BETWEEN '" + startDate + "' AND '" + endDate + "'";

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Create and populate a TransactionsPOJO object for each row in the result set
                TransactionsPOJO transaction = new TransactionsPOJO();
                transaction.setTransaction_type_id(rs.getInt("transaction_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransaction_date(rs.getDate("transaction_date"));
                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
