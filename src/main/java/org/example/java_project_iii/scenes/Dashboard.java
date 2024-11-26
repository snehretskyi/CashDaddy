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
        // check if there is a recurring transaction due
        RecurringTransactionService.processDueRecurringTransactions();

        BorderPane root = new BorderPane();

        // Create a TabPane

        TabPane tabPane = new TabPane();

        //create tabs
        AddTransaction addTransaction = new AddTransaction();
        AllTransactions allTransactions = AllTransactions.getInstance();
        UpdateTransaction updateTransaction = UpdateTransaction.getInstance();
        SummaryReport summaryReport = new SummaryReport();

        Label transactionTablabel = new Label("Add Transaction");
        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm(transactionTablabel);
        // set tab to redirect to
        createForm.setTabPane(tabPane);
        createForm.setDisplayTab(allTransactions);

        addTransaction.setContent(createForm);
        TransactionsPOJO transactionsPOJO = new TransactionsPOJO();

        Label formTitleLabel = new Label("Modify Your Transaction");

        UpdateForm updateForm = new UpdateForm("Modify Your Transaction", transactionsPOJO);
        updateTransaction.setContent(updateForm);

        tabPane.getTabs().addAll(addTransaction,allTransactions, updateTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

        root.setCenter(tabPane);


        dashboard = new Scene(root, width, height);

        //CSS
        tabPane.getStyleClass().add("dashboard-tab-pane");
        formTitleLabel.getStyleClass().add("form-title-label");

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
