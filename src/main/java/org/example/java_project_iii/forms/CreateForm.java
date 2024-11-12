package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pojo.CategoriesPOJO;
import tables.CategoriesTable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Class Description: a form designed to enter and display information to and from Database
 * Validates input.
 * @author Stan
 */
public class CreateForm extends Form {
    private String formName;
    private LocalDate date;
    private Double amount;
    private String category;
    private String budget;
    private String account;
    private String type;
    private String description;

    // getters and setters
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

    /**
     * Constructor
     * @param formName name of the form, e.g. Update
     */
    public CreateForm(String formName) throws Exception {
        super();

        this.formName = formName;

        CategoriesTable categoriesTable = new CategoriesTable();

        // creating nodes
        GridPane formGrid = new GridPane();

        Label formNameLabel = new Label(this.formName);

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // unfortunately I had to turn off text input for date
        // exception handling doesn't work as expected if user inputs invalid date (・へ・)
        datePicker.setEditable(false);

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        // for now values are hardcoded, later we'll get them from db
        Label categoryLabel = new Label("Category:");
        ComboBox<CategoriesPOJO> categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));

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

        // logic for buttons
        confirmButton.setOnAction((event) -> {
            try {
                // refuse to submit if fields are empty
                if (amountField.getText().isEmpty() || categoryComboBox.getValue() == null
                        || budgetComboBox.getValue() == null || accountComboBox.getValue() == null
                        ||  transactionTypeGroup.getSelectedToggle() == null
                        || descriptionField.getText().isEmpty()) {
                    getErrorText().setText("All fields are required!");
                    animateErrorText(getErrorText());
                } else {
                    // if not empty, the values of the fields are saved to the instance
                    // (later should be submitted to db)
                    getErrorText().setText("");
                    this.setDate(datePicker.getValue());
                    this.setAmount(Double.valueOf(amountField.getText()));
                    this.setBudget(budgetComboBox.getValue());
                    this.setAccount(accountComboBox.getValue());
                    this.setType(transactionTypeGroup.selectedToggleProperty().getName());
                    this.setDescription(descriptionField.getText().trim());
                }

            } catch (DateTimeParseException e) {
                getErrorText().setText("Wrong date format!");
                animateErrorText(getErrorText());
            } catch (NumberFormatException e) {
                getErrorText().setText("Invalid number entered!");
                animateErrorText(getErrorText());
            } catch (Exception e) {
                // generic error handling
                getErrorText().setText("Fatal Error!");
                animateErrorText(getErrorText());
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction((event) -> {
            try {
                // clears all fields
                getErrorText().setText("");
                datePicker.setValue(LocalDate.now());
                amountField.clear();
                categoryComboBox.getSelectionModel().clearSelection();
                budgetComboBox.getSelectionModel().clearSelection();
                accountComboBox.getSelectionModel().clearSelection();
                transactionTypeGroup.getSelectedToggle().setSelected(false);
                descriptionField.clear();
            } catch (Exception e) {
                // generic error handling
                getErrorText().setText("Fatal Error!");
                animateErrorText(getErrorText());
            }
        });

        // styling (˶◕‿◕˶)
        formGrid.setBorder(new Border(new BorderStroke( Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        formGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formGrid.setPadding(new Insets(20));
        formGrid.setVgap(20);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);
        formNameLabel.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(2, 2, 0, 2)
        )));
        formNameLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formNameLabel.setPadding(new Insets(5));
        transactionTypeRadioBox.setSpacing(20);

        // TODO: remove this once we add the forms to main scene
        formGrid.setMaxWidth(844);
        formGrid.setMaxHeight(480);


        this.getChildren().addAll(formNameLabel, formGrid, getErrorText());
    }
}
