package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import pojo.*;
import tables.*;
import tabs.AllTransactions;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Class Description: a form designed to enter and display information to and from Database
 * Validates input.
 * @author Stan
 */
public class UpdateForm extends Form {

    public int find(ArrayList<?> arrayList, int id){
        ArrayList<DatabaseItemPojo> searchList = (ArrayList<DatabaseItemPojo>) ((ArrayList<?>) arrayList);
        for(int i = 0; i < searchList.size(); i++){
            if(searchList.get(i).getId() == id){
                return i;
            }
        }
        return 0;
    }

    /**
     * Constructor
     */
    public UpdateForm(TransactionsPOJO transactionsPOJO) throws Exception {
        super();

        System.out.println(transactionsPOJO);

        CategoriesTable categoriesTable = CategoriesTable.getInstance();
        BudgetTable budgetTable = BudgetTable.getInstance();
        AccountsTable accountsTable = AccountsTable.getInstance();
        Transaction_typeTable transactionTypeTable = Transaction_typeTable.getInstance();
        TransactionsTable transactionsTable = TransactionsTable.getInstance();
        ArrayList<CategoriesPOJO> allCategories = categoriesTable.getAllCategories();
        ArrayList<BudgetPOJO> allBudgets = budgetTable.getAllBudgets();
        ArrayList<AccountPOJO> allAccounts = accountsTable.getAllAccounts();
        ArrayList<Transaction_typePOJO> allTransactionTypes = transactionTypeTable.getAllTransaction_types();
        RecurringTransactionTable recurringTransactionsTable = RecurringTransactionTable.getInstance();
        // try to see if it's recurring
        RecurringTransactionPOJO recurringTransaction = recurringTransactionsTable.getByTransactionId(transactionsPOJO.getId());

        // creating nodes
        GridPane formGrid = new GridPane();

        Label dateLabel = new Label("Date:");
        //DatePicker datePicker = new DatePicker(transactionsPOJO.getTransaction_date().toLocalDate());

        DatePicker datePicker = new DatePicker(
                transactionsPOJO.getTransaction_date() != null ? transactionsPOJO.getTransaction_date().toLocalDate() : null
        );


        // unfortunately I had to turn off text input for date
        // exception handling doesn't work as expected if user inputs invalid date (・へ・)
        datePicker.setEditable(false);


        //When form has no amount
        Text instructionText = new Text("Please select transaction to update");

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setText(String.valueOf(transactionsPOJO.getAmount()));

        Label categoryLabel = new Label("Category:");
        ComboBox<CategoriesPOJO> categoryBox = new ComboBox<>();
        categoryBox.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));
        categoryBox.getSelectionModel().select(find(allCategories, transactionsPOJO.getTransaction_category_id()));
        System.out.println(transactionsPOJO.getId());

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

        if (recurringTransaction != null) {
            recurringCheckBox.setSelected(true);
            recurringIntervalBox.setVisible(true);
            recurringIntervalSpinner.getValueFactory().setValue(recurringTransaction.getIntervalDays());
        }

        Label accountLabel = new Label("Account:");
        ComboBox<AccountPOJO> accountComboBox = new ComboBox<>();
        accountComboBox.setItems(FXCollections.observableArrayList(allAccounts));
        accountComboBox.getSelectionModel().select(find(allAccounts, transactionsPOJO.getTransaction_account_id()));

        Label transactionTypeLabel = new Label("Transaction Type:");

        HBox transactionTypeRadioBox = new HBox();
        ToggleGroup transactionTypeGroup = new ToggleGroup();
        ArrayList<Transaction_typePOJO> transactionTypes = allTransactionTypes;

        transactionTypes.forEach((Transaction_typePOJO transactionType) -> {
            RadioButton transactionTypeRadio = new RadioButton(transactionType.toString());
            // set pojo associated with the button
            transactionTypeRadio.setUserData(transactionType);
            transactionTypeRadioBox.getChildren().add(transactionTypeRadio);
            transactionTypeRadio.setToggleGroup(transactionTypeGroup);
        });

        transactionTypeGroup.selectToggle((Toggle) transactionTypeRadioBox.getChildren().get(find(allTransactionTypes, transactionsPOJO.getTransaction_type_id())));

        Label descriptionLabel = new Label("Description (up to 255 characters):");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setText(transactionsPOJO.getTransaction_description());

        Button confirmButton = new Button("Confirm");
        Button clearButton = new Button("Clear");

        if(transactionsPOJO.getAmount()==0){
               formGrid.add(instructionText, 0, 0);
               GridPane.setColumnSpan(instructionText, 2);
        }

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
        formGrid.add(clearButton, 5,10);
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
                } else {
                    int selectedTransactionType = ((Transaction_typePOJO) transactionTypeGroup.getSelectedToggle().getUserData()).getId();
                    TransactionsPOJO transaction = new TransactionsPOJO(transactionsPOJO.getId(),
                            accountComboBox.getSelectionModel().getSelectedItem().getId(),
                            Double.parseDouble(amountField.getText()),
                            selectedTransactionType,
                            categoryBox.getSelectionModel().getSelectedItem().getId(),
                            Date.valueOf(datePicker.getValue()),
                            descriptionArea.getText());
                    transactionsTable.updateTransaction(transaction);

                    // update recurring transaction depending on user selection
                    if (recurringCheckBox.isSelected() && recurringTransaction == null) {
                        RecurringTransactionPOJO recurringTransactionPOJO = new RecurringTransactionPOJO(0,
                                transaction.getId(), recurringIntervalSpinner.getValue());

                        recurringTransactionsTable.addRecurringTransaction(recurringTransactionPOJO);
                    } else if (!recurringCheckBox.isSelected() && recurringTransaction != null) {
                        recurringTransactionsTable.deleteRecurringTransaction(recurringTransaction.getId());
                    } else if (recurringCheckBox.isSelected() && recurringTransaction != null) {
                        recurringTransactionsTable.updateRecurringTransaction(recurringTransaction);
                    }
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

        clearButton.setOnAction((event) -> {
            try {
                // clears all fields
                getErrorText().setText("");
                datePicker.setValue(LocalDate.now());
                amountField.clear();
                categoryBox.getSelectionModel().clearSelection();
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

        // Apply styles for the GridPane
        formGrid.getStyleClass().add("form-grid");

        // Apply styles for the form name label
        dateLabel.getStyleClass().add("form-label");
        amountLabel.getStyleClass().add("form-label");
        categoryLabel.getStyleClass().add("form-label");
        recurringLabel.getStyleClass().add("form-label");
        accountLabel.getStyleClass().add("form-label");
        transactionTypeLabel.getStyleClass().add("form-label");
        descriptionLabel.getStyleClass().add("form-label");
        instructionText.getStyleClass().add("instruction-text");

        confirmButton.getStyleClass().add("confirm-button");
        clearButton.getStyleClass().add("clear-button");

        // Apply styles for the transaction type radio box
        transactionTypeRadioBox.getStyleClass().add("transaction-type-radio-box");
        VBox.setMargin(formGrid, new Insets(25));

        this.getChildren().addAll(formGrid, getErrorText());
    }

}
