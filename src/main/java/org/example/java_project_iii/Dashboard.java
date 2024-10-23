package org.example.java_project_iii;

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

public class Dashboard extends Application {


    @Override
    public void start(Stage stage) throws Exception {

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

        Scene scene = new Scene(root,1280,720);
        stage.setTitle("Welcome to CashDaddy!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
