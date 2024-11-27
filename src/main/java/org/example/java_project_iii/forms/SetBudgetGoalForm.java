package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SetBudgetGoalForm {

    private ComboBox<String> categoryComboBox;
    private TextField goalAmountField;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> budgetTypeComboBox;


    /**
     * Budget form layout which retuens VBox.
     */
    public VBox budgetForm() {
        VBox budgetFormContainer = new VBox();

        // Title
        Label titleLabel = new Label("Set Budget Goal");

        HBox categoryBox = new HBox(10);
        Label categoryLabel = new Label("Category:");
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(FXCollections.observableArrayList("Groceries", "Rent", "Utilities", "Others"));
        categoryComboBox.setPromptText("Select Category");
        categoryBox.getChildren().addAll(categoryLabel, categoryComboBox);



        goalAmountField = new TextField();
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        budgetTypeComboBox = new ComboBox<>();
        return budgetFormContainer;

    }




}
