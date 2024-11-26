package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pojo.*;
import tables.*;
import tabs.AllTransactions;

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

    private Tab displayTab;

    private TabPane tabPane;

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getDisplayTab() {
        return displayTab;
    }

    public void setDisplayTab(Tab displayTab) {
        this.displayTab = displayTab;
    }

    /**
     * Constructor
     */
    public CreateForm() throws Exception {
        super();

        CategoriesTable categoriesTable = CategoriesTable.getInstance();
        AccountsTable accountsTable = AccountsTable.getInstance();
        Transaction_typeTable transactionTypeTable = Transaction_typeTable.getInstance();
        TransactionsTable transactionsTable = TransactionsTable.getInstance();
        RecurringTransactionTable recurringTransactionsTable = RecurringTransactionTable.getInstance();

        // creating nodes
        GridPane formGrid = new GridPane();

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
        Spinner<Integer> recurringIntervalSpinner = new Spinner<>(0, 100, 1);
        recurringIntervalBox.getChildren().addAll(recurringIntervalLabel, recurringIntervalSpinner);
        // hide the box unless it is selected
        recurringIntervalBox.setVisible(false);

        // if checkbox is selected, add ability to enter interval
        recurringCheckBox.setOnAction((event) -> {
            recurringIntervalBox.setVisible(recurringCheckBox.isSelected());
        });

        Label categoryLabel = new Label("Category:");
        ComboBox<CategoriesPOJO> categoryBox = new ComboBox<>();
        categoryBox.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));

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
        formGrid.add(categoryBox, 1, 3);
        formGrid.add(recurringLabel, 0, 4);
        formGrid.add(recurringCheckBox, 1, 4);
        formGrid.add(recurringIntervalBox, 1, 5);
        formGrid.add(accountLabel, 0, 6);
        formGrid.add(accountComboBox, 1, 6);
        formGrid.add(transactionTypeLabel, 0, 7);
        formGrid.add(transactionTypeRadioBox, 1, 7);
        formGrid.add(descriptionLabel, 0, 9);
        formGrid.add(descriptionArea, 1, 9);

        formGrid.add(confirmButton, 4, 10);
        formGrid.add(cancelButton, 5, 10);

        // logic for buttons
        confirmButton.setOnAction((event) -> {
            try {
                // refuse to submit if fields are empty
                if (amountField.getText().isEmpty()
                        || categoryBox.getSelectionModel().getSelectedItem() == null
                        || accountComboBox.getValue() == null
                        ||  transactionTypeGroup.getSelectedToggle() == null
                        || descriptionArea.getText().isEmpty()) {
                    getErrorText().setText("All fields are required!");
                    animateErrorText(getErrorText());
                } else if (descriptionArea.getText().length() > 255) {
                    // check if the description is over 255 characters
                    getErrorText().setText("Description is over 255 characters!");
                    animateErrorText(getErrorText());
                } else if (Double.parseDouble(amountField.getText()) > 99999999.99) {
                    getErrorText().setText("We only support transactions with value up to $99,999,999.99");
                    animateErrorText(getErrorText());
                }
                else {
                    int selectedTransactionType = ((Transaction_typePOJO) transactionTypeGroup.getSelectedToggle().getUserData()).getId();
                    TransactionsPOJO transaction = new TransactionsPOJO(0,
                            accountComboBox.getSelectionModel().getSelectedItem().getId(),
                            Double.parseDouble(amountField.getText()),
                            selectedTransactionType,
                            categoryBox.getSelectionModel().getSelectedItem().getId(),
                            Date.valueOf(datePicker.getValue()),
                            descriptionArea.getText());
                    transactionsTable.addTransaction(transaction);

                    if (recurringCheckBox.isSelected()) {
                        RecurringTransactionPOJO recurringTransactionPOJO = new RecurringTransactionPOJO(0,
                                transaction.getId(), recurringIntervalSpinner.getValue());

                        recurringTransactionsTable.addRecurringTransaction(recurringTransactionPOJO);
                    }

                    // clears all fields
                    getErrorText().setText("");
                    datePicker.setValue(LocalDate.now());
                    amountField.clear();
                    categoryBox.getSelectionModel().clearSelection();
                    recurringCheckBox.setSelected(false);
                    accountComboBox.getSelectionModel().clearSelection();
                    transactionTypeGroup.getSelectedToggle().setSelected(false);
                    descriptionArea.clear();

                    tabPane.getSelectionModel().select(displayTab);

                    AllTransactions.getInstance().refreshTable();

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
                categoryBox.getSelectionModel().clearSelection();
                recurringCheckBox.setSelected(false);
                recurringIntervalSpinner.setVisible(false);
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
        formGrid.getStyleClass().add("form-grid");

        // Apply styles for the form name label
        dateLabel.getStyleClass().add("form-label");
        amountLabel.getStyleClass().add("form-label");
        categoryLabel.getStyleClass().add("form-label");
        recurringLabel.getStyleClass().add("form-label");
        accountLabel.getStyleClass().add("form-label");
        transactionTypeLabel.getStyleClass().add("form-label");
        descriptionLabel.getStyleClass().add("form-label");
        confirmButton.getStyleClass().add("confirm-button");
        cancelButton.getStyleClass().add("clear-button");

        VBox.setMargin(formGrid, new Insets(0, 25, 25, 25));

        // Apply styles for the transaction type radio box
        transactionTypeRadioBox.getStyleClass().add("transaction-type-radio-box");

        // Load the CSS file
        formGrid.getStylesheets().add(getClass().getClassLoader().getResource("css/forms.css").toExternalForm());

        this.getChildren().addAll(getErrorText(),formGrid);
    }
}
