package org.example.java_project_iii.scenes;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.java_project_iii.forms.CreateForm;
import org.example.java_project_iii.forms.UpdateForm;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import org.example.java_project_iii.services.RecurringTransactionService;
import org.example.java_project_iii.tabs.*;


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

    private static TabPane tabPane = new TabPane();

    private static Tab allTransactions;

    static {
        try {
            allTransactions = AllTransactions.getInstance();
            } catch (Exception e) {
            throw new RuntimeException(e);
       }
    }

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


    public static TabPane getTabPane() {
        return tabPane;
    }

    public static void setTabPane(TabPane tabPane) {
        Dashboard.tabPane = tabPane;
    }

    public static Tab getAllTransactions() {
        return allTransactions;
    }

    public static void setAllTransactions(Tab allTransactions) {
        Dashboard.allTransactions = allTransactions;
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


        //create org.example.java_project_iii.tabs
        AddTransaction addTransaction = new AddTransaction();
        AllTransactions allTransactions = AllTransactions.getInstance();
        UpdateTransaction updateTransaction = UpdateTransaction.getInstance();
        SummaryReport summaryReport = new SummaryReport();
        BudgetGoalTracker budgetGoalTracker = new BudgetGoalTracker();

        Label transactionTablabel = new Label("Add Transaction");
        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm();

        // set tab to redirect to
        createForm.setTabPane(tabPane);
        createForm.setDisplayTab(allTransactions);

        addTransaction.setContent(createForm);
        TransactionsPOJO transactionsPOJO = new TransactionsPOJO();

        UpdateForm updateForm = new UpdateForm(transactionsPOJO);
        updateTransaction.setContent(updateForm);

        // Add org.example.java_project_iii.tabs to the TabPane

        //addTransaction.getContent().setRotate(90);

        tabPane.getTabs().addAll(addTransaction,allTransactions, updateTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

        root.setCenter(tabPane);


        dashboard = new Scene(root, width, height);

        //CSS
        tabPane.getStyleClass().add("dashboard-tab-pane");

        //Assign styles to each tab
        addTransaction.getStyleClass().add("tab-add-transaction");
        allTransactions.getStyleClass().add("tab-all-transactions");
        updateTransaction.getStyleClass().add("tab-update-transaction");
        summaryReport.getStyleClass().add("tab-summary-report");


        root.getStylesheets().add(getClass().getClassLoader().getResource("css/dashboard.css").toExternalForm());

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
