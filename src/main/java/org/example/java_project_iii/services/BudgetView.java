package org.example.java_project_iii.services;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionTypePOJO;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionTypeTable;

import java.util.List;

/**
 * Handles the budget view, including budgets table and progress chart
 */
public class BudgetView {

    private static BudgetView instance;
    private static int selectedID;
    private VBox budgetViewVBox;
    private TableView<BudgetPOJO> tableView;
    private LineChart<Number, Number> goalProgressChart;
    private BorderPane parentLayout;

    //Getters and setters for selectedId
    public static int getSelectedID() {
        return selectedID;
    }

    public static void setSelectedID(int selectedID) {
        BudgetView.selectedID = selectedID;
    }

    /**
     * Constructor to initialize budget view (Singleton pattern)
     *
     * @throws Exception
     */
    private BudgetView() throws Exception {

        // Initialize TableView
        tableView = new TableView();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

        TableColumn<BudgetPOJO, Double> goalAmountColumn = new TableColumn("Goal Amount");
        goalAmountColumn.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getGoal_amount()).asObject());

        TableColumn<BudgetPOJO, String> startDateColumn = new TableColumn("Start Date");
        startDateColumn.setCellValueFactory(e -> new SimpleObjectProperty(e.getValue().getStart_date().toString()));

        TableColumn<BudgetPOJO, String> endDateColumn = new TableColumn("End Date");
        endDateColumn.setCellValueFactory(e -> new SimpleObjectProperty(e.getValue().getEnd_date().toString()));

        // Add columns to tableView
        tableView.getColumns().addAll(transactionTypeColumn, goalAmountColumn, startDateColumn, endDateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Populate TableView
        refreshTable(budgetTable);

        // Remove Button
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            List<BudgetPOJO> selectedItems = tableView.getSelectionModel().getSelectedItems();
            selectedItems.forEach(item -> budgetTable.removeBudget(item.getId())); // Add removeBudget method in BudgetTable
            try {
                updateChart(selectedID);
                refreshTable(budgetTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Row double-click action for updating the chart
        tableView.setRowFactory(tv -> {
            TableRow<BudgetPOJO> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    BudgetPOJO selectedBudget = row.getItem();
                    try {
                        if (selectedBudget != null) {
                            // Pass the selected budget ID to update the chart
                            selectedID = selectedBudget.getId();
                            updateChart(selectedID);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return row;
        });

        // Layout

        HBox buttonBox = new HBox(removeButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        budgetViewVBox = new VBox(10, tableView, buttonBox);
        budgetViewVBox.setPadding(new Insets(20));

        //Apply styles to nodes, load the stylesheet
        tableView.getStyleClass().add("table-view");
        removeButton.getStyleClass().add("button");

        budgetViewVBox.getStylesheets().add(this.getClass().getResource("/css/displayTransactions.css").toExternalForm());

    }

    /**
     * Sets the parent layout to update the chart
     *
     * @param parentLayout border pane
     */
    public void setParentLayout(BorderPane parentLayout) {
        this.parentLayout = parentLayout;
    }

    /**
     * Updates the chart based on the selectedID
     *
     * @param budgetId budget id related to selected row
     * @throws Exception if any error
     */
    public void updateChart(int budgetId) throws Exception {
        // Get the instance of BarChartGenerator
        BarChartGenerator chartGenerator = BarChartGenerator.getInstance();

        // Generate the new chart with the selected budget ID
        BarChart<String, Number> updatedChart = chartGenerator.createGoalProgressBarChart(budgetId);

        parentLayout.setCenter(updatedChart);
    }

    /**
     * Refresh budget table
     *
     * @param budgetTable
     * @throws Exception if any error
     */
    public void refreshTable(BudgetTable budgetTable) throws Exception {
        tableView.getItems().clear();
        tableView.getItems().addAll(budgetTable.getAllBudgets());
    }

    /**
     * @return single instance of BudgetView
     * @throws Exception
     */
    public static BudgetView getInstance() throws Exception {
        if (instance == null) {
            instance = new BudgetView();
        }
        return instance;
    }

    /**
     * @return VBox layout
     */
    public VBox BudgetView() {
        return budgetViewVBox;
    }
}
