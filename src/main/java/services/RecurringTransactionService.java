package services;

import pojo.RecurringTransactionPOJO;
import pojo.TransactionsPOJO;
import tables.RecurringTransactionTable;
import tables.TransactionsTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

public class RecurringTransactionService {

    /**
     * Method to calculate next date for recurring transaction
     * @param transactionDate date of transaction
     * @param intervalDays interval in days
     * @return new date
     */
    public static Date calculateNewDate(Date transactionDate, int intervalDays) {
        return Date.valueOf(transactionDate.toLocalDate().plusDays(intervalDays));
    }

    /**
     * Method to copy existing recurring transaction, update the information about next one accordingly
     * @param oldRecurringTransaction recurring transaction that serves as a foundation
     * @return updated recurring transaction with new date and transaction id
     * @throws Exception
     */
    public static RecurringTransactionPOJO createNewTransaction(RecurringTransactionPOJO oldRecurringTransaction) throws Exception {
        TransactionsTable transactionsTable = TransactionsTable.getInstance();
        RecurringTransactionTable recurringTransactionTable = RecurringTransactionTable.getInstance();

        // creating new transaction with same data
        TransactionsPOJO transactionToCopy = transactionsTable.getTransaction(oldRecurringTransaction.getTransactionId());
        TransactionsPOJO newTransaction = new TransactionsPOJO(0,
                transactionToCopy.getTransaction_account_id(),
                transactionToCopy.getAmount(),
                transactionToCopy.getTransaction_type_id(),
                transactionToCopy.getTransaction_category_id(),
                // date is updated
                calculateNewDate(transactionToCopy.getTransaction_date(), oldRecurringTransaction.getIntervalDays()),
                transactionToCopy.getTransaction_description());
        transactionsTable.addTransaction(newTransaction);

        // updating the recurring transaction
        RecurringTransactionPOJO newRecurringTransaction = new RecurringTransactionPOJO(oldRecurringTransaction.getId(),
                newTransaction.getId(),
                oldRecurringTransaction.getIntervalDays());
        recurringTransactionTable.updateRecurringTransaction(newRecurringTransaction);

        return  newRecurringTransaction;
    }

    /**
     * This method goes through <i>ALL</i> recurring transactions and updates the ones that are due <br>
     * logic works like this:
     * <ol>
     *     <li>Is this transaction due?</li>
     *     <ol>
     *         <li>If yes:</li>
     *         <li>Create new copy of a transaction</li>
     *         <li>Change its referenced id</li>
     *         <li>Add the interval in days to the date</li>
     *         <li>Repeat above steps until it is not due</li>
     *     </ol>
     *     <ol>
     *         <li>If no:</li>
     *         <li>Skip</li>
     *     </ol>
     * </ol>
     * @throws Exception
     */
    public static void processDueRecurringTransactions() throws Exception {
        RecurringTransactionTable recurringTransactionTable = RecurringTransactionTable.getInstance();
        ArrayList<RecurringTransactionPOJO> allRecurringTransaction = recurringTransactionTable.getAllRecurringTransactions();

        // go through all known recurring transactions
        allRecurringTransaction.forEach((RecurringTransactionPOJO recurringTransaction) -> {
            LocalDate nextDate = recurringTransaction.getNextDate().toLocalDate();
            LocalDate today = LocalDate.now();
            // if time is due, create new transaction
            while (!nextDate.isAfter(today)) {
                try {
                    recurringTransaction = createNewTransaction(recurringTransaction);
                    nextDate = recurringTransaction.getNextDate().toLocalDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
