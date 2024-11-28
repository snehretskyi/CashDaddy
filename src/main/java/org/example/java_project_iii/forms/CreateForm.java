package org.example.java_project_iii.forms;

import org.example.java_project_iii.pojo.*;

import java.time.LocalDate;
import java.sql.Date;

/**
 * Class Description: a form designed to submit new item to the org.example.java_project_iii.database
 */
public class CreateForm extends CrudForm {

    /**
     * Constructor. Nice and simple. Parent class handles everything
     */
    public CreateForm() throws Exception {
        super();
    }

    /**
     * Gets data from the fields, creates new pojos accordingly. Creates both new transaction, and recurring transaction
     * @throws Exception
     */
    public void submit() throws Exception {
        int selectedTransactionType = ((TransactionTypePOJO) transactionTypeGroup.getSelectedToggle().getUserData()).getId();
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
    }

    /**
     * Simply clears all fields, makes recurring transaction invisible again
     */
    public void clear() {
        // clears all fields
        getErrorText().setText("");
        datePicker.setValue(LocalDate.now());
        amountField.clear();
        categoryBox.getSelectionModel().clearSelection();
        recurringCheckBox.setSelected(false);
        recurringIntervalBox.setVisible(false);
        accountComboBox.getSelectionModel().clearSelection();
        transactionTypeGroup.getSelectedToggle().setSelected(false);
        descriptionArea.clear();
    }
}
