package org.example.java_project_iii.forms;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionTypePOJO;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionTypeTable;

import java.time.LocalDate;
import java.util.ArrayList;

public class SetBudgetGoalForm extends VBox {
    private static SetBudgetGoalForm instance;

    private TextField amountField;
    private ArrayList<CheckBox> transactionTypeCheckBoxes;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    private SetBudgetGoalForm() throws Exception {

        // Create VBox for form
        VBox formVBox = new VBox(15);
        formVBox.setPadding(new Insets(20));

        // Start Date
        Label startDateLabel = new Label("Start Date:");
        startDatePicker = new DatePicker(LocalDate.now());

        // End Date
        Label endDateLabel = new Label("End Date:");
        endDatePicker = new DatePicker(LocalDate.now().plusMonths(1));

        // Amount
        Label amountLabel = new Label("Goal Amount:");
        amountField = new TextField();
        amountField.setPromptText("Enter budget goal amount");

        // Transaction Types
        Label transactionTypeLabel = new Label("Transaction Types:");
        TransactionTypeTable transactionTypeTable = TransactionTypeTable.getInstance();
        ArrayList<TransactionTypePOJO> allTransactionTypes = transactionTypeTable.getAllTransaction_types();

        VBox transactionTypeCheckBoxGroup = new VBox(5);
        transactionTypeCheckBoxes = new ArrayList<>();
        for (TransactionTypePOJO type : allTransactionTypes) {
            CheckBox checkBox = new CheckBox(type.toString());
            transactionTypeCheckBoxes.add(checkBox);
            transactionTypeCheckBoxGroup.getChildren().add(checkBox);
        }

        // Buttons
        Button confirmButton = new Button("Confirm");
        Button clearButton = new Button("Clear");

        HBox buttonHBox = new HBox(10, confirmButton, clearButton);
        buttonHBox.setStyle("-fx-alignment: center;");

        // Add event handling
        confirmButton.setOnAction(event -> {
            try {
                if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || amountField.getText().isEmpty()) {
                    showError("All fields are required!");
                } else if (Double.parseDouble(amountField.getText()) <= 0) {
                    showError("Amount must be greater than zero!");
                } else {
                    ArrayList<String> selectedTypes = new ArrayList<>();
                    for (CheckBox checkBox : transactionTypeCheckBoxes) {
                        if (checkBox.isSelected()) {
                            selectedTypes.add(checkBox.getText());
                        }
                    }
                    if (selectedTypes.isEmpty()) {
                        showError("At least one transaction type must be selected!");
                    } else {
                        // Save the budget goal
                        saveBudgetGoal();
                        clearForm();
                    }
                }
            } catch (NumberFormatException e) {
                showError("Invalid amount entered!");
            } catch (Exception e) {
                showError("An unexpected error occurred!");
                e.printStackTrace();
            }
        });

        clearButton.setOnAction(event -> clearForm());

        // Add nodes to form VBox
        formVBox.getChildren().addAll(
                startDateLabel, startDatePicker,
                endDateLabel, endDatePicker,
                amountLabel, amountField,
                transactionTypeLabel, transactionTypeCheckBoxGroup,
                buttonHBox
        );

        this.getChildren().add(formVBox);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusMonths(1));
        amountField.clear();
        for (CheckBox checkBox : transactionTypeCheckBoxes) {
            checkBox.setSelected(false);
        }
    }

    private void saveBudgetGoal() throws Exception {
        double goalAmount = getGoalAmount();
        if (goalAmount <= 0) {
            return;
        }

        // Retrieve selected transaction types
        ArrayList<String> selectedTypes = new ArrayList<>();
        for (CheckBox checkBox : transactionTypeCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedTypes.add(checkBox.getText());
            }
        }

        // Example of saving the budget goal to the database (you need to implement the actual database interaction)
        BudgetPOJO budget = new BudgetPOJO(
                0, // This will be auto-generated by the DB
                1, // Example Category ID (you should set this appropriately)
                goalAmount,
                java.sql.Date.valueOf(startDatePicker.getValue()),
                java.sql.Date.valueOf(endDatePicker.getValue())
        );

        BudgetTable budgetTable = BudgetTable.getInstance();
        // Assuming you have a method in BudgetTable to insert a budget (implement this as needed)
        budgetTable.insertBudget(budget);  // You will need to create this method in BudgetTable

        System.out.println("Budget saved!");
    }

    public double getGoalAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return 0.0;  // If there's an issue parsing, return 0
        }
    }

    public double getProgress() {
        double progress = 0.0;
        for (CheckBox checkBox : transactionTypeCheckBoxes) {
            if (checkBox.isSelected()) {
                progress += 10.0;  // Assuming each selected transaction type contributes 10 units of progress
            }
        }
        return progress;
    }

    public static SetBudgetGoalForm getInstance() throws Exception {
        if (instance == null) {
            instance = new SetBudgetGoalForm();
        }
        return instance;
    }
}
