package org.example.java_project_iii.tabs;

import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import org.example.java_project_iii.forms.SetBudgetGoalForm;
import org.example.java_project_iii.services.BarChartGenerator;
import org.example.java_project_iii.services.BudgetView;

public class BudgetGoalTracker extends Tab{

    private BarChart<String, Number> progressChart;
    private LineChart<Number, Number> goalProgressChart;
    public BudgetGoalTracker() throws Exception {
        // Set the title of the tab
        this.setText("Budget Goal Tracker");

       BorderPane root = new BorderPane();

       VBox vBoxFormTable = new VBox();
       vBoxFormTable.getChildren().addAll(SetBudgetGoalForm.getInstance(),BudgetView.getInstance().BudgetView() );

        BarChartGenerator chartGenerator = BarChartGenerator.getInstance();

        // Assuming budgetId is the ID of the budget you want to generate the chart for
        int budgetId = 8; // Replace with the actual budget ID

        // Generate the bar chart using the BarChartGenerator
        BarChart<String, Number> goalProgressChart = chartGenerator.createGoalProgressBarChart(budgetId);

        // Set the form VBox to the left and the chart to the center
        root.setLeft(vBoxFormTable);
        root.setCenter(goalProgressChart);

        // Set the root BorderPane as the content of the Tab
        this.setContent(root);

    }


}
