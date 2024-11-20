package tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import pojo.DisplayTransaction;
import pojo.TransactionsPOJO;
import tables.TransactionsTable;

public class AllTransactions extends Tab {

    /**
     * Singleton class AllTransactions .
     */

    private static AllTransactions instance;
    public TableView tableView;

    private AllTransactions() throws Exception {

        this.setText("Transactions");
        TransactionsTable transactionsTable = TransactionsTable.getInstance();

        BorderPane borderPane = new BorderPane();

        tableView = new TableView();


        /**
         * Column1 for displaying account name
         */

        TableColumn<DisplayTransaction, String> column1 =
                new TableColumn<>("Account Name");

        column1.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getAccount_name()));

        /**
         * Column2 for displaying transaction amount
         */

        TableColumn<DisplayTransaction, String> column2 =
                new TableColumn<>("Transaction Amount");

        column2.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getAmount()));

        /**
         * Column3 for displaying transaction type
         */

        TableColumn<DisplayTransaction, String> column3 =
                new TableColumn<>("Transaction Type");

        column3.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getType()));

        /**
         * Column6 for displaying category type
         */
        TableColumn<DisplayTransaction, String> column6 =
                new TableColumn<>("Category Type");

        column6.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getCategory()));


        /**
         * Column4 for displaying transaction date
         */

        TableColumn<DisplayTransaction, String> column4 =
                new TableColumn<>("Transction Date");

        column4.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDate()));

        /**
         * Column5 for displaying transaction description
         */

        TableColumn<DisplayTransaction, String> column5 =
                new TableColumn<>("Transction Description");

        column5.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDescription()));

        /**
         * added all columns to tableView
         */

        tableView.getColumns().addAll(column1, column2, column3, column6, column4, column5);
        tableView.getItems().addAll(transactionsTable.getDetailedTransaction());

        /**
         * Add tableView to the center of borderpane
         */
        borderPane.setCenter(tableView);

        /**
         * set width in pixels
         */
        column1.setPrefWidth(200);
        column2.setPrefWidth(150);
        column3.setPrefWidth(200);
        column4.setPrefWidth(200);
        column5.setPrefWidth(100);
        column6.setPrefWidth(80);

        /**
         * Button responsible to remove transaction
         */
        Button removeTransaction = new Button("Remove Transaction");
        removeTransaction.setOnAction(e-> {
            DisplayTransaction remove = (DisplayTransaction) tableView.getSelectionModel().getSelectedItem();
            transactionsTable.deleteTransaction(remove.getId());
            try {
                refreshTable();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            tableView.getItems().clear();;
            tableView.getItems().addAll(transactionsTable.getDetailedTransaction());
        });

        //set remove transaction button at the bottom of the borderpane
        borderPane.setBottom(removeTransaction);
        this.setContent(borderPane);

    }

    /**
     * Refreshes the table view by clearing current items and reloading transaction data.
     * @throws Exception
     */
    public void refreshTable() throws Exception {
        TransactionsTable table = TransactionsTable.getInstance();
        tableView.getItems().clear();
        tableView.getItems().addAll(table.getDetailedTransaction());
        System.out.println("Table refreshed");
    }


    public static AllTransactions getInstance() throws Exception {
        if(instance == null){
            instance = new AllTransactions();
        }
        return instance;
    }

}
