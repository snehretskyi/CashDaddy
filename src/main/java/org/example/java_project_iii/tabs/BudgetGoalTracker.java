package org.example.java_project_iii.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleDoubleProperty;
import org.example.java_project_iii.forms.SetBudgetGoalForm;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.services.BudgetView;
import org.example.java_project_iii.services.LineChartGenerator;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionsTable;

import java.util.ArrayList;

public class BudgetGoalTracker extends Tab{

    private BarChart<String, Number> progressChart;
    private LineChart<Number, Number> goalProgressChart;
    public BudgetGoalTracker() throws Exception {
        // Set the title of the tab
        this.setText("Budget Goal Tracker");

       BorderPane root = new BorderPane();

       VBox vBoxFormTable = new VBox();
       vBoxFormTable.getChildren().addAll(SetBudgetGoalForm.getInstance(),BudgetView.getInstance().BudgetView() );

        LineChartGenerator chartGenerator = LineChartGenerator.getInstance();

        // Assuming budgetId is the ID of the budget you want to generate the chart for
        int budgetId = 3; // Replace with actual budget ID

        // Generate the chart using the LineChartGenerator
        goalProgressChart = chartGenerator.createGoalProgressChart(budgetId);

        // Add the generated chart to the center of the BorderPane
        root.setCenter(goalProgressChart);
        // Set the form VBox to the left and the chart to the center
        root.setLeft(vBoxFormTable);
        root.setCenter(goalProgressChart);

        // Set the root BorderPane as the content of the Tab
        this.setContent(root);

    }


}
