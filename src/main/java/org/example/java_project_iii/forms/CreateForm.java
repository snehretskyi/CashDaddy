package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pojo.*;
import tables.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Class Description: a form designed to enter and display information to and from Database
 * Validates input.
 * @author Stan
 */
public class CreateForm extends Form {
    private String formName;

    /**
     * Constructor
     * @param formName name of the form, e.g. Update
     */
    public CreateForm(String formName) throws Exception {
        super();

        this.formName = formName;

        CategoriesTable categoriesTable = new CategoriesTable();
        AccountsTable accountsTable = new AccountsTable();
        Transaction_typeTable transactionTypeTable = new Transaction_typeTable();
        TransactionsTable transactionsTable = new TransactionsTable();
        Transaction_categoryTable transactionCategoryTable = new Transaction_categoryTable();

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

        Label recurringLabel = new Label("Recurring?");
        CheckBox recurringCheckBox = new CheckBox();

        VBox recurringIntervalBox = new VBox();
        Label recurringIntervalLabel = new Label("Interval (in days)");
        Spinner recurringIntervalSpinner = new Spinner(0, 100, 1);
        recurringIntervalBox.getChildren().addAll(recurringIntervalLabel, recurringIntervalSpinner);
        // hide the box unless it is selected
        recurringIntervalBox.setVisible(false);

        // if checkbox is selected, add ability to enter interval
        recurringCheckBox.setOnAction((event) -> {
            recurringIntervalBox.setVisible(recurringCheckBox.isSelected());
        });

        Label categoryLabel = new Label("Category:");
        ListView<CategoriesPOJO> categoryListView = new ListView<>();
        categoryListView.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));
        categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Label accountLabel = new Label("Account:");
        ComboBox<AccountPOJO> accountComboBox = new ComboBox<>();
        accountComboBox.setItems(FXCollections.observableArrayList(accountsTable.getAllAccounts()));


        Label transactionTypeLabel = new Label("Transaction Type:");

        HBox transactionTypeRadioBox = new HBox();
        ToggleGroup transactionTypeGroup = new ToggleGroup();
        ArrayList<Transaction_typePOJO> transactionTypes = transactionTypeTable.getAllTransaction_types();

        transactionTypes.forEach((Transaction_typePOJO transactionType) -> {
            RadioButton transactionTypeRadio = new RadioButton(transactionType.toString());
            // set pojo associated with the button
            transactionTypeRadio.setUserData(transactionType);
            transactionTypeRadioBox.getChildren().add(transactionTypeRadio);
            transactionTypeRadio.setToggleGroup(transactionTypeGroup);
        });

        Label descriptionLabel = new Label("Description (up to 255 characters):");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3);

        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        formGrid.add(dateLabel, 0, 1);
        formGrid.add(datePicker, 1, 1);
        formGrid.add(amountLabel, 0, 2);
        formGrid.add(amountField, 1, 2);
        formGrid.add(categoryLabel, 0, 3);
        formGrid.add(categoryListView, 1, 3);
        formGrid.add(recurringLabel, 0, 4);
        formGrid.add(recurringCheckBox, 1, 4);
        formGrid.add(recurringIntervalBox, 0, 5);
        formGrid.add(accountLabel, 0, 6);
        formGrid.add(accountComboBox, 1, 6);
        formGrid.add(transactionTypeLabel, 0, 7);
        formGrid.add(transactionTypeRadioBox, 0, 8);
        formGrid.add(descriptionLabel, 0, 9);
        formGrid.add(descriptionArea, 1, 9);

        formGrid.add(confirmButton, 4, 10);
        formGrid.add(cancelButton, 5, 10);

        // logic for buttons
        confirmButton.setOnAction((event) -> {
            try {
                // refuse to submit if fields are empty
                if (amountField.getText().isEmpty()
                        || categoryListView.getSelectionModel().getSelectedItem() == null
                        || accountComboBox.getValue() == null
                        ||  transactionTypeGroup.getSelectedToggle() == null
                        || descriptionArea.getText().isEmpty()) {
                    getErrorText().setText("All fields are required!");
                    animateErrorText(getErrorText());
                } else if (descriptionArea.getText().length() > 255) {
                    // check if the description is over 255 characters
                    getErrorText().setText("Description is over 255 characters!");
                    animateErrorText(getErrorText());
                }
                else {
                    int selectedTransactionType = ((Transaction_typePOJO) transactionTypeGroup.getSelectedToggle().getUserData()).getId();
                    TransactionsPOJO transaction = new TransactionsPOJO(0,
                            accountComboBox.getSelectionModel().getSelectedItem().getId(),
                            Double.parseDouble(amountField.getText()),
                            selectedTransactionType,
                            Date.valueOf(datePicker.getValue()),
                            descriptionArea.getText());
                    transactionsTable.addTransaction(transaction);

                    ArrayList<CategoriesPOJO> selectedCategories = new ArrayList<>(categoryListView.getSelectionModel().getSelectedItems());

                    selectedCategories.forEach((CategoriesPOJO category) -> {
                        Transaction_categoryPOJO transactionCategoryJunction = new Transaction_categoryPOJO(transaction.getId(), category.getId());
                        transactionCategoryTable.addTransaction_category(transactionCategoryJunction);
                    });

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
                categoryListView.getSelectionModel().clearSelection();
                recurringCheckBox.setSelected(false);
                accountComboBox.getSelectionModel().clearSelection();
                transactionTypeGroup.getSelectedToggle().setSelected(false);
                descriptionArea.clear();
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


        this.getChildren().addAll(formNameLabel, formGrid, getErrorText());
    }
}
