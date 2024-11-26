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
        // Load the CSS file
        tabPane.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());


        //create tabs
        AddTransaction addTransaction = new AddTransaction();
        AllTransactions allTransactions = AllTransactions.getInstance();
        UpdateTransaction updateTransaction = UpdateTransaction.getInstance();
        SummaryReport summaryReport = new SummaryReport();

        //add CurdForm to AddTransaction tab
        CreateForm createForm = new CreateForm();
        // set tab to redirect to
        createForm.setTabPane(tabPane);
        createForm.setDisplayTab(allTransactions);

        addTransaction.setContent(createForm);
        TransactionsPOJO transactionsPOJO = new TransactionsPOJO();

        UpdateForm updateForm = new UpdateForm(transactionsPOJO);
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
