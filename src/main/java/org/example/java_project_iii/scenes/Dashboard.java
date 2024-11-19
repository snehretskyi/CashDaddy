package org.example.java_project_iii.scenes;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.java_project_iii.forms.CreateForm;
import org.example.java_project_iii.forms.UpdateForm;
import pojo.TransactionsPOJO;
import tables.TransactionsTable;
import tabs.*;


/**
 * Class for dashboard scene. Uses singleton pattern.
 * @author Riddhi, <sub>modififed by Stan</sub>
 */
public class Dashboard {

    private Scene dashboard;
    private static Dashboard dashboardInstance;

    // default values for width and height
    private int width = 1280;
    private int height = 720;

    // getters and setters
    public Scene getDashboardScene() {
        return dashboard;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Private constructor.
     */
    private Dashboard() throws Exception {
        BorderPane root = new BorderPane();

        // Build a MenuBar
        MenuBar menu = new MenuBar();

        Menu year = new Menu("Year");
        Menu income = new Menu("Income");
        Menu expanses = new Menu("Expenses");
        Menu savings = new Menu("Savings");


        // add the menu item to the menu bar

        menu.getMenus().addAll(year, income, expanses, savings);


        // Create a TabPane

        TabPane tabPane = new TabPane();

        //create tabs
        AddTransaction addTransaction = new AddTransaction();
        AllTransactions allTransactions = AllTransactions.getInstance();
        UpdateTransaction updateTransaction = new UpdateTransaction();
        SummaryReport summaryReport = new SummaryReport();
        DeleteTransaction deleteTransaction= new DeleteTransaction();

        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm("Add Transaction");
        addTransaction.setContent(createForm);

// --------------------------------------------------------------------------------------------------

        // UNCOMMENT AND INSERT THE ID TO TEST

        VBox updateItem = new VBox();
        Button buttonTest = new Button("Test update form");
        buttonTest.setOnAction((event) -> {
            TransactionsTable testTransactionsTable = null;
            try {
                testTransactionsTable = new TransactionsTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            TransactionsPOJO testTransaction = testTransactionsTable.getTransaction(16);
            UpdateForm updateForm = null;
            try {
                updateForm = new UpdateForm("Update Transaction", testTransaction);
                updateItem.getChildren().add(updateForm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        updateItem.getChildren().add(buttonTest);
        updateTransaction.setContent(updateItem);

 //-----------------------------------------------------------------------------------------------


        // Add tabs to the TabPane

        //addTransaction.getContent().setRotate(90);
        tabPane.getTabs().addAll(addTransaction,allTransactions, updateTransaction, deleteTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

        root.setTop(menu);
        root.setCenter(tabPane);


        dashboard = new Scene(root, width, height);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene. If no return null as it can't construct.
     * @return scene Login scene
     */
    public static Scene getDashboardSceneInstance() throws Exception {
        if (dashboardInstance == null) {
            dashboardInstance = new Dashboard();
        }

        return dashboardInstance.getDashboardScene();
    }
}
