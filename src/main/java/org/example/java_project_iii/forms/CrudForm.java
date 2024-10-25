package org.example.java_project_iii.forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;

public class CrudForm extends VBox {
    private String formName;
    private LocalDate date;
    private Double amount;
    private String category;
    private String budget;
    private String account;
    private String type;
    private String description;


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getAccount() {
        return account;
    }

    public String getBudget() {
        return budget;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public CrudForm(String formName) {
        this.formName = formName;

        GridPane formGrid = new GridPane();

        Label formNameLabel = new Label(this.formName);

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Recreation", "Rent", "Lorem Ipsum");

        Label budgetLabel = new Label("Budget:");
        ComboBox<String> budgetComboBox = new ComboBox<>();
        budgetComboBox.getItems().addAll("Lorem Ipsum", "Mental Health", "Allowance");

        Label accountLabel = new Label("Account:");
        ComboBox<String> accountComboBox = new ComboBox<>();
        accountComboBox.getItems().addAll("RBC Savings", "TD Debt", "CIBC Investments");

        Label transactionTypeLabel = new Label("Transaction Type:");

        HBox transactionTypeRadioBox = new HBox();
        RadioButton incomeRadio = new RadioButton("Income");
        RadioButton expenseRadio = new RadioButton("Expense");
        RadioButton savingsRadio = new RadioButton("Savings");
        transactionTypeRadioBox.getChildren().addAll(incomeRadio, expenseRadio, savingsRadio);

        ToggleGroup transactionTypeGroup = new ToggleGroup();
        incomeRadio.setToggleGroup(transactionTypeGroup);
        expenseRadio.setToggleGroup(transactionTypeGroup);
        savingsRadio.setToggleGroup(transactionTypeGroup);



        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        
        formGrid.setPadding(new Insets(20));
        formGrid.setVgap(20);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        formGrid.add(dateLabel, 0, 1);
        formGrid.add(datePicker, 1, 1);
        formGrid.add(amountLabel, 0, 2);
        formGrid.add(amountField, 1, 2);
        formGrid.add(categoryLabel, 0, 3);
        formGrid.add(categoryComboBox, 1, 3);
        formGrid.add(budgetLabel, 0, 4);
        formGrid.add(budgetComboBox, 1, 4);
        formGrid.add(accountLabel, 0, 5);
        formGrid.add(accountComboBox, 1, 5);
        formGrid.add(transactionTypeLabel, 0, 6);
        formGrid.add(transactionTypeRadioBox, 0, 7);
        formGrid.add(descriptionLabel, 0, 8);
        formGrid.add(descriptionField, 1, 8);

        formGrid.add(confirmButton, 4, 9);
        formGrid.add(cancelButton, 5, 9);

        confirmButton.setOnAction((event) -> {
            try {
                this.setDate(datePicker.getValue());
                this.setAmount(Double.valueOf(amountField.getText()));
                this.setCategory(categoryComboBox.getValue());
                this.setBudget(budgetComboBox.getValue());
                this.setAccount(accountComboBox.getValue());
                this.setType(transactionTypeGroup.selectedToggleProperty().getName());
                this.setDescription(descriptionField.getText().trim());
            } catch (Error e) {
                System.out.println("FATAL ERROR!!!");
            }
        });

        cancelButton.setOnAction((event) -> {
            try {
                datePicker.setValue(LocalDate.now());
                amountField.clear();
                categoryComboBox.getSelectionModel().clearSelection();
                budgetComboBox.getSelectionModel().clearSelection();
                accountComboBox.getSelectionModel().clearSelection();
                transactionTypeGroup.getSelectedToggle().setSelected(false);
                descriptionField.clear();
            } catch (Error e) {
                System.out.println("FATAL ERROR!!!");
            }
        });

        formGrid.setBorder(new Border(new BorderStroke( Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        formGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formNameLabel.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(2, 2, 0, 2)
        )));
        formNameLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formNameLabel.setPadding(new Insets(5));
        transactionTypeRadioBox.setSpacing(20);


        formGrid.setMaxWidth(844);
        formGrid.setMaxHeight(480);


        this.getChildren().addAll(formNameLabel, formGrid);
    }
}
