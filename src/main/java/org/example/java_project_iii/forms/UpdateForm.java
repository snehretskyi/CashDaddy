package org.example.java_project_iii.forms;

import javafx.scene.control.*;
import org.example.java_project_iii.scenes.Dashboard;
import org.example.java_project_iii.pojo.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class Description: a form designed to update existing transaction
 */
public class UpdateForm extends CrudForm {
    private RecurringTransactionPOJO recurringTransaction;
    private TransactionsPOJO transactionsPOJO;

    // getters and setters
    public TransactionsPOJO getTransactionsPOJO() {
        return transactionsPOJO;
    }

    public void setTransactionsPOJO(TransactionsPOJO transactionsPOJO) {
        this.transactionsPOJO = transactionsPOJO;
    }


    public RecurringTransactionPOJO getRecurringTransaction() {
        return recurringTransaction;
    }

    public void setRecurringTransaction(RecurringTransactionPOJO recurringTransaction) {
        this.recurringTransaction = recurringTransaction;
    }

    /**
     * Updates both transaction and recurring transaction. <br>
     * Checks how exactly has user changed recurring transaction, reacts accordingly.
     *
     * @throws Exception
     */
    @Override
    public void submit() throws Exception {
        int selectedTransactionType = ((TransactionTypePOJO) transactionTypeGroup.getSelectedToggle().getUserData()).getId();

        // try to see if it's recurring
        recurringTransactionsTable.getByTransactionId(transactionsPOJO.getId());

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
            // update interval days
            recurringTransaction.setIntervalDays(recurringIntervalSpinner.getValue());
            recurringTransaction.calculateNextDate();
            recurringTransactionsTable.updateRecurringTransaction(recurringTransaction);
        }
    }

    /**
     * Clears all fields, makes recurring transaction invisible again, gets rid of transaction org.example.java_project_iii.pojo
     */
    @Override
    public void clear() {
        // clears all fields
        getErrorText().setText("");
        datePicker.setValue(LocalDate.now());
        amountField.clear();
        categoryBox.getSelectionModel().clearSelection();
        recurringCheckBox.setSelected(false);
        accountComboBox.getSelectionModel().clearSelection();
        transactionTypeGroup.getSelectedToggle().setSelected(false);
        descriptionArea.clear();
        this.transactionsPOJO = null;
        this.recurringTransaction = null;
        getErrorText().setText("Please Select Transaction To Update");
        confirmButton.setDisable(true);

    }

    /**
     * Method to find db item
     *
     * @param arrayList of items
     * @param id        of item to find
     * @return id of an item you're looking for OR 0
     */
    public int find(ArrayList<?> arrayList, int id) {
        ArrayList<DatabaseItemPojo> searchList = (ArrayList<DatabaseItemPojo>) ((ArrayList<?>) arrayList);
        for (int i = 0; i < searchList.size(); i++) {
            if (searchList.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Constructor, populates the fields with data from transaction
     */
    public UpdateForm(TransactionsPOJO transactionsPOJO) throws Exception {
        super();

        // set tab to redirect to (unlike Create form, there can be multiple update forms created throughout app,
        // can't just setTab once)
        tabPane = Dashboard.getTabPane();
        displayTab = Dashboard.getAllTransactions();

        this.transactionsPOJO = transactionsPOJO;
        this.recurringTransaction = recurringTransactionsTable.getByTransactionId(transactionsPOJO.getId());
        // getting values from db
        datePicker.setValue(transactionsPOJO.getTransaction_date() != null ? transactionsPOJO.getTransaction_date().toLocalDate() : null);

        amountField.setText(String.valueOf(transactionsPOJO.getAmount()));

        categoryBox.getSelectionModel().select(find(allCategories, transactionsPOJO.getTransaction_category_id()));

        if (recurringTransaction != null) {
            System.out.println(recurringTransaction.getIntervalDays());
            recurringCheckBox.setSelected(true);
            recurringIntervalBox.setVisible(true);
            recurringIntervalSpinner.getValueFactory().setValue(recurringTransaction.getIntervalDays());
        }

        accountComboBox.getSelectionModel().select(find(allAccounts, transactionsPOJO.getTransaction_account_id()));

        transactionTypeGroup.selectToggle((Toggle) transactionTypeRadioBox.getChildren().get(find(allTransactionTypes, transactionsPOJO.getTransaction_type_id())));

        descriptionArea.setText(transactionsPOJO.getTransaction_description());

        if (transactionsPOJO.getAmount() == 0) {
            getErrorText().setText("Please Select Transaction To Update");
            confirmButton.setDisable(true);
        }

        // change text to clear
        cancelButton.setText("Clear");

        // styling (˶◕‿◕˶)
        cancelButton.getStyleClass().add("clear-button");

    }

}
