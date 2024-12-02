package org.example.java_project_iii.tabs;

import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.forms.SetBudgetGoalForm;
import org.example.java_project_iii.services.BudgetView;
import org.example.java_project_iii.tables.BudgetTable;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.java_project_iii.database.DBConst.TABLE_BUDGETS;

/**
 * Tab for tracking budget goals
 */
public class BudgetGoalTracker extends Tab {

    //class members
    private BarChart<String, Number> progressChart;
    private LineChart<Number, Number> goalProgressChart;
    Database db;

    /**
     * Constructor
     * Sets up the tab with a form, table and chart
     *
     * @throws Exception is any error occur
     */
    public BudgetGoalTracker() throws Exception {
        // Set the title of the tab
        this.setText("Budget Goal Tracker");
        db = Database.getInstance();
        BorderPane root = new BorderPane();

        VBox vBoxFormTable = new VBox();
        vBoxFormTable.getChildren().addAll(SetBudgetGoalForm.getInstance(), BudgetView.getInstance().BudgetView());

        BudgetView budgetView = BudgetView.getInstance();

        budgetView.setParentLayout(root);
        root.setLeft(vBoxFormTable);

        //select first id from budget table to show the chart
        String query = "SELECT budget_id FROM " + TABLE_BUDGETS + " ORDER BY budget_id ASC LIMIT 1";
        Statement statement = db.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            BudgetView.setSelectedID(resultSet.getInt("budget_id"));
            System.out.println("First Budget ID: " + BudgetView.getSelectedID());

            // Update the chart with the selected ID
            budgetView.updateChart(BudgetView.getSelectedID());
        }

        // Refresh the budget table
        budgetView.refreshTable(BudgetTable.getInstance());

        // Set the root BorderPane as the content of the Tab
        this.setContent(root);

    }

}
