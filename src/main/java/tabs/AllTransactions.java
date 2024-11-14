package tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TableView;

public class AllTransactions extends Tab {

    /**
     * Singleton class AllTransactions .
     */

    private static AllTransactions instance;
    public TableView tableView;

    private AllTransactions(){

        this.setText("Transactions");



    }


    public static AllTransactions getInstance(){
        if(instance == null){
            instance = new AllTransactions();
        }
        return instance;
    }

}
