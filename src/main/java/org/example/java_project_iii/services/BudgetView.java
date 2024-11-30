package org.example.java_project_iii.services;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionTypePOJO;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionTypeTable;
import org.example.java_project_iii.tables.TransactionsTable;

import java.util.List;

public class BudgetView {
    private static BudgetView instance;
    private VBox budgetViewVBox;
    private TableView<BudgetPOJO> tableView;
    private LineChart<Number, Number> goalProgressChart;

    private BudgetView() throws Exception {
        // Initialize TableView
        tableView = new TableView<>();
        BudgetTable budgetTable = BudgetTable.getInstance();
        TransactionTypeTable transactionTypeTable = TransactionTypeTable.getInstance();

        // Columns
        TableColumn<BudgetPOJO, String> transactionTypeColumn = new TableColumn<>("Transaction Type");
        transactionTypeColumn.setCellValueFactory(e -> {
            try {
                // Fetch the TransactionTypePOJO using the transaction ID
                TransactionTypePOJO transactionType = TransactionTypeTable.getInstance()
                        .getTransaction_type(e.getValue().getTransaction_id());

                // Return the transaction type as a SimpleStringProperty
                return new SimpleStringProperty(transactionType != null ? transactionType.getTransactionType() : "Unknown");
            } catch (Exception ex) {
                ex.printStackTrace();
                // Return an error string if something goes wrong
                return new SimpleStringProperty("Error");
            }
        });

        TableColumn<BudgetPOJO, Double> goalAmountColumn = new TableColumn<>("Goal Amount");
        goalAmountColumn.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getGoal_amount()).asObject());

        TableColumn<BudgetPOJO, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getStart_date().toString()));

        TableColumn<BudgetPOJO, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getEnd_date().toString()));

        // Add columns to tableView
        tableView.getColumns().addAll(transactionTypeColumn, goalAmountColumn, startDateColumn, endDateColumn);

        // Populate TableView
        refreshTable(budgetTable);

        // Remove Button
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            List<BudgetPOJO> selectedItems = tableView.getSelectionModel().getSelectedItems();
            selectedItems.forEach(item -> budgetTable.removeBudget(item.getId())); // Add removeBudget method in BudgetTable
            try {
                refreshTable(budgetTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //See Your progress button
        Button seeYourProgress = new Button("Track Progress");
        seeYourProgress.setOnAction(event -> {
            BudgetPOJO selectedBudget = tableView.getSelectionModel().getSelectedItem();
            if (selectedBudget != null) {
                try {
                    // Update the chart data based on the selected budget ID
                    //updateChartData(selectedBudget.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No budget selected.");
            }
        });



        // Layout

        HBox buttonBox = new HBox(10, seeYourProgress, removeButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        budgetViewVBox = new VBox(10, tableView, buttonBox);
        budgetViewVBox.setPadding(new Insets(20));
    }

//    private void updateChartData(int budgetId) throws Exception {
//        BudgetPOJO selectedBudget = BudgetTable.getInstance().getBudget(budgetId);
//        List<TransactionsPOJO> transactions = TransactionsTable.getInstance().getTransactionsForBudget(budgetId);
//
//        double totalSpent = 0.0;
//        for (TransactionsPOJO transaction : transactions) {
//            if (transaction.getTransaction_date().after(selectedBudget.getStart_date()) && transaction.getTransaction_date().before(selectedBudget.getEnd_date())) {
//                totalSpent += transaction.getAmount();
//            }
//        }
//
//        double goalAmount = selectedBudget.getGoal_amount();
//        double percentageAchieved = (totalSpent / goalAmount) * 100;
//
//        if (goalProgressChart != null) {
//            goalProgressChart.getData().clear();
//        }
//
//        XYChart.Series<Number, Number> progressSeries = new XYChart.Series<>();
//        progressSeries.setName("Goal Progress");
//        progressSeries.getData().add(new XYChart.Data<>(0, 0));
//        progressSeries.getData().add(new XYChart.Data<>(1, percentageAchieved));
//
//        if (goalProgressChart == null) {
//            goalProgressChart = new LineChart<>(new NumberAxis(), new NumberAxis());
//            goalProgressChart.getData().add(progressSeries);
//        } else {
//            goalProgressChart.getData().add(progressSeries);
//        }
//    }


    public void refreshTable(BudgetTable budgetTable) throws Exception {
        tableView.getItems().clear();
        tableView.getItems().addAll(budgetTable.getAllBudgets());
    }

    public static BudgetView getInstance() throws Exception {
        if (instance == null) {
            instance = new BudgetView();
        }
        return instance;
    }

    public VBox BudgetView() {
        return budgetViewVBox;
    }
}
