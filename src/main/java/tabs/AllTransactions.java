package tabs;

import javafx.beans.property.SimpleStringProperty;
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

    }


    public static AllTransactions getInstance() throws Exception {
        if(instance == null){
            instance = new AllTransactions();
        }
        return instance;
    }

}
