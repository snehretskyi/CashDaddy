package org.example.java_project_iii.tabs;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.java_project_iii.forms.UpdateForm;
import org.example.java_project_iii.pojo.DisplayTransaction;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.tables.TransactionsTable;

import java.sql.Date;
import java.util.List;

/**
 * Tab for viewing and managing all transactions
 * Uses a singleton pattern
 */

public class AllTransactions extends Tab {

    // Singleton instance
    private static AllTransactions instance;

    // Table to display transactions
    public TableView tableView;

    /**
     * Private constructor to initialize the tab.
     *
     * @throws Exception if data fetching fails
     */
    private AllTransactions() throws Exception {

        this.setText("Transactions");
        this.getStyleClass().add("tab-add-transaction");
        TransactionsTable transactionsTable = TransactionsTable.getInstance();

        BorderPane borderPane = new BorderPane();

        Text instructionText = new Text("Double click to modify any transaction");
        VBox instructionBox = new VBox(instructionText);
        instructionText.setTextAlignment(TextAlignment.CENTER);
        instructionBox.setAlignment(Pos.CENTER);
        VBox.setMargin(instructionText, new Insets(5, 0, 5, 0));

        tableView = new TableView();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        /**
         * Column1 for displaying account name
         */

        TableColumn<DisplayTransaction, String> column1 =
                new TableColumn<>("Account");

        column1.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getAccount_name()));

        /**
         * Column2 for displaying transaction amount
         */

        TableColumn<DisplayTransaction, Double> column2 =
                new TableColumn<>("Amount");

        column2.setCellValueFactory(
                e -> new SimpleDoubleProperty(e.getValue().getAmount()).asObject());

        /**
         * Column3 for displaying transaction type
         */

        TableColumn<DisplayTransaction, String> column3 =
                new TableColumn<>("Type");

        column3.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getType()));

        /**
         * Column6 for displaying category type
         */
        TableColumn<DisplayTransaction, String> column6 =
                new TableColumn<>("Category");

        column6.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getCategory()));


        /**
         * Column4 for displaying transaction date
         */

        TableColumn<DisplayTransaction, Date> column4 = new TableColumn<>("Date");

        column4.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getDate()));

        /**
         * Column5 for displaying transaction description
         */

        TableColumn<DisplayTransaction, String> column5 =
                new TableColumn<>("Description");

        column5.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getDescription()));

        /**
         * Column7 for displaying recurring transaction status
         */

        TableColumn<DisplayTransaction, String> column7 =
                new TableColumn<>("Recurring?");
        column7.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getRecurringStatus()));

        /**
         * Column8 for displaying recurring transaction days
         */

        TableColumn<DisplayTransaction, String> column8 =
                new TableColumn<>("Interval (Days)");
        column8.setCellValueFactory(
                e -> new SimpleStringProperty(e.getValue().getIntervalDays()));

        /**
         * Add all columns to tableView
         */

        tableView.getColumns().addAll(column1, column2, column3, column6, column4, column5, column7, column8);
        tableView.getItems().addAll(transactionsTable.getDetailedTransaction());

        /**
         * Add tableView to the center of borderpane
         */
        borderPane.setTop(instructionBox);
        borderPane.setCenter(tableView);

        /**
         * Button to remove transactions
         */
        HBox buttonBox = new HBox();
        Button removeTransaction = new Button("Remove");
        removeTransaction.setOnAction(e -> {
            List<DisplayTransaction> remove = tableView.getSelectionModel().getSelectedItems();
            remove.forEach((DisplayTransaction transaction) -> {
                transactionsTable.deleteTransaction(transaction.getId());
            });

            try {
                refreshTable();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            tableView.getItems().clear();
            ;
            tableView.getItems().addAll(transactionsTable.getDetailedTransaction());
        });
        buttonBox.getChildren().add(removeTransaction);
        buttonBox.setAlignment(Pos.CENTER);
        HBox.setMargin(removeTransaction, new Insets(10));

        //set remove transaction button at the bottom of the borderpane
        borderPane.setBottom(buttonBox);
        this.setContent(borderPane);

        // Handle double-click to edit a transaction
        tableView.setRowFactory(tv -> {
            TableRow<DisplayTransaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    DisplayTransaction selectedTransaction = row.getItem();
                    try {
                        // Fetch the detailed TransactionsPOJO
                        TransactionsPOJO transactionsPOJO = TransactionsTable.getInstance().getTransactionById(selectedTransaction.getId());
                        UpdateTransaction updateTransaction = UpdateTransaction.getInstance();
                        // Create a new UpdateForm
                        UpdateForm updateForm = new UpdateForm(transactionsPOJO);
                        updateTransaction.setContent(updateForm);

                        // Add the new tab to the tab pane and activate it
                        // TabsContainer.getInstance().addOrActivateTab(updateTransaction);
                        getTabPane().getSelectionModel().select(updateTransaction);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            return row;
        });

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        //Add CSS style classes to nodes
        tableView.getStyleClass().add("table-view");
        instructionText.getStyleClass().add("instruction-text");
        removeTransaction.getStyleClass().add("button");

        borderPane.getStylesheets().add(this.getClass().getResource("/css/displayTransactions.css").toExternalForm());

    }

    /**
     * Refreshes the table with the latest transaction data
     *
     * @throws Exception if data loading fails
     */
    public void refreshTable() throws Exception {
        TransactionsTable table = TransactionsTable.getInstance();
        tableView.getItems().clear();
        tableView.getItems().addAll(table.getDetailedTransaction());
        System.out.println("Table refreshed");
    }

    /**
     * Returns the singleton instance of AllTransactions
     *
     * @return instance of this class
     * @throws Exception if any error
     */

    public static AllTransactions getInstance() throws Exception {
        if (instance == null) {
            instance = new AllTransactions();
        }
        return instance;
    }


}
