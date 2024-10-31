package org.example.java_project_iii.scenes;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tabs.AddTransaction;
import tabs.DeleteTransaction;
import tabs.SummaryReport;
import tabs.UpdateTransaction;

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

    // getter
    public static Dashboard getDashboardInstance() {
        return dashboardInstance;
    }

    /**
     * Private constructor.
     */
    private Dashboard() {
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
        UpdateTransaction updateTransaction = new UpdateTransaction();
        SummaryReport summaryReport = new SummaryReport();
        DeleteTransaction deleteTransaction= new DeleteTransaction();


        // Add tabs to the TabPane

        //addTransaction.getContent().setRotate(90);
        tabPane.getTabs().addAll(addTransaction, updateTransaction, deleteTransaction, summaryReport);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);

        root.setTop(menu);
        root.setCenter(tabPane);

        dashboard = new Scene(root);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene
     * @return scene Dashboard scene
     */
    public Scene getDashboard() {
        if (dashboardInstance == null) {
            dashboardInstance = new Dashboard();
        }

        return dashboardInstance.getDashboard();
    }
}
