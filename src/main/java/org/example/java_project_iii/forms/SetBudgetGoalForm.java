package org.example.java_project_iii.forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.pojo.TransactionTypePOJO;
import org.example.java_project_iii.services.BudgetView;
import org.example.java_project_iii.tables.BudgetTable;
import org.example.java_project_iii.tables.TransactionTypeTable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Form for setting a budget goal (Singleton)
 */
public class SetBudgetGoalForm extends Form {

    private static SetBudgetGoalForm instance;

    private TextField amountField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ToggleGroup transactionTypeGroup;
    private HBox transactionTypeRadioBox;
    private HBox dateBox;

    /**
     * Constructor for SetBudgetGoalForm
     *
     * @throws Exception if there is an error
     */
    private SetBudgetGoalForm() throws Exception {

        // Database instances
        BudgetTable budgetTable = BudgetTable.getInstance();
        TransactionTypeTable transactionTypeTable = TransactionTypeTable.getInstance();
        ArrayList<TransactionTypePOJO> transactionTypes = transactionTypeTable.getAllTransaction_types();

        // VBox for the form
        VBox formVBox = new VBox(15);
        formVBox.setPadding(new Insets(20));

        //Instruction On Top
        Label instructionLabel = new Label("Choose your goal");

        // Start Date
        Label startDateLabel = new Label("Start Date:");
        startDatePicker = new DatePicker(LocalDate.now());

        // End Date
        Label endDateLabel = new Label("End Date:");
        endDatePicker = new DatePicker(LocalDate.now().plusMonths(1));

        //Dates side by side
        VBox startDateVbox = new VBox(10, startDateLabel, startDatePicker);
        VBox endDateVbox = new VBox(10, endDateLabel, endDatePicker);
        dateBox = new HBox(20, startDateVbox, endDateVbox);

        // Amount
        Label amountLabel = new Label("Goal Amount:");
        amountField = new TextField();
        amountField.setPromptText("Enter budget goal amount");

        // Transaction Types
        Label transactionTypeLabel = new Label("Transaction Type:");
        transactionTypeGroup = new ToggleGroup();
        transactionTypeRadioBox = new HBox(10);

        //Amount and transactions side by side
        VBox amountVbox = new VBox(10, amountLabel, amountField);
        VBox transactionTypeVbox = new VBox(10, transactionTypeLabel, transactionTypeRadioBox);
        HBox amountTransactionHbox = new HBox(60, amountVbox, transactionTypeVbox);

        //create radio buttons for each transactions type
        transactionTypes.forEach(transactionType -> {
            RadioButton transactionTypeRadio = new RadioButton(transactionType.toString());
            transactionTypeRadio.setToggleGroup(transactionTypeGroup);
            transactionTypeRadio.setUserData(transactionType);
            transactionTypeRadioBox.getChildren().add(transactionTypeRadio);
        });

        // Buttons
        Button confirmButton = new Button("Confirm");
        Button clearButton = new Button("Clear");

        HBox buttonHBox = new HBox(10, confirmButton, clearButton);
        buttonHBox.setStyle("-fx-alignment: center;");

        // Add event handling
        confirmButton.setOnAction(event -> {
            try {
                // Validate fields
                if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || amountField.getText().isEmpty() || transactionTypeGroup.getSelectedToggle() == null) {
                    getErrorText().setText("All fields are required!");
                    animateErrorText(getErrorText());
                } else if (Double.parseDouble(amountField.getText()) <= 0) {
                    getErrorText().setText("The amount must be > 0");
                    animateErrorText(getErrorText());
                } else {
                    // Create and insert a new budget
                    TransactionTypePOJO selectedTransactionType =
                            (TransactionTypePOJO) transactionTypeGroup.getSelectedToggle().getUserData();
                    BudgetPOJO budget = new BudgetPOJO(
                            0,
                            selectedTransactionType.getId(),
                            Double.parseDouble(amountField.getText()),
                            Date.valueOf(startDatePicker.getValue()),
                            Date.valueOf(endDatePicker.getValue())
                    );
                    budgetTable.insertBudget(budget);

                    // Refresh budget view
                    BudgetView budgetView = BudgetView.getInstance();
                    budgetView.refreshTable(BudgetTable.getInstance());

                    // Clear the form
                    clearForm();
                }
            } catch (NumberFormatException e) {
                getErrorText().setText("Invalid amount entered!");
                animateErrorText(getErrorText());
            } catch (Exception e) {
                getErrorText().setText("An unexpected error occurred!");
                animateErrorText(getErrorText());
                e.printStackTrace();
            }
        });

        clearButton.setOnAction(event -> clearForm());

        this.getChildren().clear();

        // Add nodes to form VBox
        formVBox.getChildren().addAll(
                instructionLabel,
                dateBox,
                amountTransactionHbox,
                buttonHBox,
                getErrorText()
        );

        this.getChildren().add(formVBox);
        this.setAlignment(Pos.TOP_CENTER);

        // Apply CSS class names to nodes
        instructionLabel.getStyleClass().add("instruction-text");
        startDateLabel.getStyleClass().add("form-label");
        endDateLabel.getStyleClass().add("form-label");
        amountLabel.getStyleClass().add("form-label");
        transactionTypeRadioBox.getStyleClass().add("transaction-type-radio-box");
        confirmButton.getStyleClass().add("confirm-button");
        clearButton.getStyleClass().add("clear-button");

        this.getStylesheets().add(getClass().getClassLoader().getResource("css/forms.css").toExternalForm());

    }

    /**
     * Clear the form fields
     */
    private void clearForm() {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusMonths(1));
        amountField.clear();
        transactionTypeGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setSelected(false));
        getErrorText().setText("");
    }

    /**
     * Get the singleton instance of SetBudgetForm
     *
     * @return same single instance
     * @throws Exception if any error
     */
    public static SetBudgetGoalForm getInstance() throws Exception {
        if (instance == null) {
            instance = new SetBudgetGoalForm();
        }
        return instance;
    }
}
