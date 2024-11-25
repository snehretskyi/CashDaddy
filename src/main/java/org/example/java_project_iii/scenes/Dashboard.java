package org.example.java_project_iii.scenes;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.java_project_iii.forms.CreateForm;
import org.example.java_project_iii.forms.UpdateForm;
import pojo.TransactionsPOJO;
import services.RecurringTransactionService;
import tables.TransactionsTable;
import tabs.*;


/**
 * Class for dashboard scene. Uses singleton pattern.
 * @author Riddhi, <sub>modififed by Stan</sub>
 */
public class Dashboard {

    private Scene dashboard;
    private static Dashboard dashboardInstance;
    private static TabPane tabPane = new TabPane();

    private static Tab allTransactions;

    static {
        try {
            allTransactions = AllTransactions.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // default values for width and height
    private int width = 1280;
    private int height = 720;

    // getters and setters
    public static Tab getAllTransactions() {
        return allTransactions;
    }

    public static void setAllTransactions(Tab allTransactions) {
        Dashboard.allTransactions = allTransactions;
    }

    public static TabPane getTabPane() {
        return tabPane;
    }

    public static void setTabPane(TabPane tabPane) {
        Dashboard.tabPane = tabPane;
    }
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
        // check if there is a recurring transaction due
        RecurringTransactionService.processDueRecurringTransactions();

        BorderPane root = new BorderPane();

        // Create a TabPane

        //create tabs
        AddTransaction addTransaction = new AddTransaction();
        UpdateTransaction updateTransaction = UpdateTransaction.getInstance();
        SummaryReport summaryReport = new SummaryReport();

        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm("Add Transaction");

        addTransaction.setContent(createForm);
        TransactionsPOJO transactionsPOJO = new TransactionsPOJO();

        UpdateForm updateForm = new UpdateForm("Update transaction details", transactionsPOJO);
        updateTransaction.setContent(updateForm);

        // Add tabs to the TabPane

        //addTransaction.getContent().setRotate(90);
        tabPane.getTabs().addAll(addTransaction,allTransactions, updateTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

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
