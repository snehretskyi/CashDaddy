package tabs;

import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tab;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
        import javafx.scene.layout.*;
        import javafx.beans.property.SimpleDoubleProperty;
import org.example.java_project_iii.forms.SetBudgetGoalForm;
import pojo.BudgetPOJO;
import pojo.TransactionsPOJO;
import tables.BudgetTable;
import tables.TransactionsTable;

import java.util.ArrayList;

public class BudgetGoalTracker extends Tab{

    private BarChart<String, Number> progressChart;
    private TextField amountField;
    private DatePicker startDatePicker, endDatePicker;
    private ArrayList<CheckBox> transactionTypeCheckBoxes;
    private SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

    public BudgetGoalTracker() throws Exception {
        // Set the title of the tab
        this.setText("Budget Goal Tracker");

       BorderPane root = new BorderPane();

       //SetBudgetGoalForm formVBox =

        // Create the chart
        progressChart = createChart();

        // Set the form VBox to the left and the chart to the center
        root.setLeft(SetBudgetGoalForm.getInstance());
        root.setCenter(progressChart);

        // Set the root BorderPane as the content of the Tab
        this.setContent(root);

        // Simulate real-time updates by adding listener on transactions
        setupRealTimeTransactionListener();
    }

    // Method to create the progress chart
    private BarChart<String, Number> createChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Budget Goal Progress");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Progress");

        // Add initial dummy data (this will be updated dynamically)
        series.getData().add(new XYChart.Data<>("Budget Goal", progress.get()));
        chart.getData().add(series);

        return chart;
    }

    // Method to set the budget goal
    private void setBudgetGoal() {
        try {
            double goalAmount = Double.parseDouble(amountField.getText());
            if (goalAmount <= 0) {
                showError("Goal amount must be greater than zero.");
                return;
            }

            // Create a budget POJO (you would insert it into the database)
            BudgetPOJO newBudget = new BudgetPOJO(
                    0, // Dummy ID, real ID would come from the DB
                    1, // Dummy category ID
                    goalAmount,
                    java.sql.Date.valueOf(startDatePicker.getValue()),
                    java.sql.Date.valueOf(endDatePicker.getValue())
            );

            // Save to database (call your DB table method here)
            BudgetTable.getInstance().addBudget(newBudget);

            // Update the chart (this is just a placeholder, implement the logic for real-time updates)
            updateChart(goalAmount);
        } catch (NumberFormatException e) {
            showError("Invalid amount entered!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update the chart progress (this is just an example, adjust based on your logic)
    private void updateChart(double goalAmount) {
        progress.set(goalAmount);  // Update the goal progress (this is simplified)
        progressChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Progress");
        series.getData().add(new XYChart.Data<>("Budget Goal", progress.get()));
        progressChart.getData().add(series);
    }

    // Method to simulate real-time updates from transactions (you would link this to your transaction table)
    private void setupRealTimeTransactionListener() throws Exception {
        TransactionsTable transactionTable = TransactionsTable.getInstance();

//        // Simulate listening to changes in transactions
//        transactionTable.addTransactionListener((TransactionsPOJO transaction) -> {
//            // Calculate the new progress
//            double actualSpending = calculateActualSpending();
//            updateChart(actualSpending);
//        });
  }

    // Method to calculate the actual spending (you would implement this logic)
    private double calculateActualSpending() {
        // Placeholder: Simulate a calculation based on transactions
        return 150.0;  // Return actual spending (replace this with real data)
    }

    // Method to show error alerts
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
