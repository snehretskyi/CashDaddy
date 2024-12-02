package org.example.java_project_iii.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import org.example.java_project_iii.database.DBConst;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.tables.TransactionsTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BarChartTab extends Tab {
    private static BarChartTab instance;
    private BarChart<String, Number> barChart;

    private BarChartTab() {
        this.setText("Transactions by Date");

        // Set up the axes for the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Transaction Amount");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Transactions Summed by Date");

        // Button to refresh the chart - is it needed though?
        HBox refreshBox = new HBox();
        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> generateChart());
        refreshBox.setAlignment(Pos.CENTER);
        HBox.setMargin(refresh, new Insets(20));
        refreshBox.getChildren().add(refresh);


        BorderPane root = new BorderPane();
        root.setCenter(barChart);
        root.setBottom(refreshBox);

        this.setContent(root);

        root.getStylesheets().add(getClass().getClassLoader().getResource("css/forms.css").toExternalForm());
        refresh.getStyleClass().add("confirm-button");

        generateChart();
    }

    public void generateChart() {
        ArrayList<TransactionsPOJO> transactions = fetchTransactionsGroupedByDate();

        ArrayList<BarChart.Data<String, Number>> chartData = new ArrayList<>();

        for (TransactionsPOJO transaction : transactions) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(transaction.getTransaction_date());

            chartData.add(new BarChart.Data<>(formattedDate, transaction.getAmount()));
        }

        BarChart.Series<String, Number> series = new BarChart.Series<>();
        series.setName("Transaction Sums");

        // cleart the chart first
        barChart.getData().clear();
        series.getData().addAll(chartData);

        barChart.getData().add(series);

    }

    private ArrayList<TransactionsPOJO> fetchTransactionsGroupedByDate() {
        ArrayList<TransactionsPOJO> transactions = new ArrayList<>();
        String query = "SELECT TRANSACTION_DATE, SUM(AMOUNT) as total_amount " +
                "FROM " + DBConst.TABLE_TRANSACTIONS + " " +
                "GROUP BY TRANSACTION_DATE " +
                "ORDER BY TRANSACTION_DATE";

        try {
            ResultSet resultSet = TransactionsTable.getInstance().getDb().getConnection().createStatement().executeQuery(query);

            while (resultSet.next()) {
                java.sql.Date transactionDate = resultSet.getDate("TRANSACTION_DATE");
                double totalAmount = resultSet.getDouble("total_amount");

                transactions.add(new TransactionsPOJO(totalAmount, transactionDate, ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public static BarChartTab getInstance() {
        if (instance == null) {
            instance = new BarChartTab();
        }
        return instance;
    }
}
