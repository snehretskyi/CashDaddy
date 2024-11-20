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
 * Class for dashboard. Uses singleton pattern.
 * I'm <i>sure</i> there's a better way to create a separate class for scenes, but
 * last sem it wasn't explained properly.
 * TODO: check if it can be done better.
 * @author Riddhi, <sub>modififed by Stan</sub>
 */
public class Dashboard {

    private Scene dashboard;
    private static Dashboard dashboardInstance;

    private int width;
    private int height;

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
    private Dashboard(int width, int height) throws Exception {
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

        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm("Add Transaction");
        addTransaction.setContent(createForm);

// --------------------------------------------------------------------------------------------------

        // UNCOMMENT AND INSERT THE ID TO TEST

//        VBox updateItem = new VBox();
//        Button buttonTest = new Button("Test update form");
//        buttonTest.setOnAction((event) -> {
//            TransactionsTable testTransactionsTable = null;
//            try {
//                testTransactionsTable = new TransactionsTable();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            TransactionsPOJO testTransaction = testTransactionsTable.getTransaction(16);
//            UpdateForm updateForm = null;
//            try {
//                updateForm = new UpdateForm("Update Transaction", testTransaction);
//                updateItem.getChildren().add(updateForm);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//        });
//        updateItem.getChildren().add(buttonTest);
//        updateTransaction.setContent(updateItem);

 //-----------------------------------------------------------------------------------------------


        // Add tabs to the TabPane

        //addTransaction.getContent().setRotate(90);
        tabPane.getTabs().addAll(addTransaction,allTransactions, updateTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

        root.setTop(menu);
        root.setCenter(tabPane);


        dashboard = new Scene(root, width, height);
    }

    /**
     * As the class needs some arguments (width and height), it's necessary to first set it up
     */
    public static void createDashboard(int width, int height) throws Exception {
        dashboardInstance = new Dashboard(width, height);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene. If no return null as it can't construct.
     * @return scene Login scene
     */
    public static Scene getDashboardSceneInstance() {
        if (dashboardInstance == null) {
            System.out.println("Please ensure you have already created the scene.");
            return null;
        }

        return dashboardInstance.getDashboardScene();
    }
}
