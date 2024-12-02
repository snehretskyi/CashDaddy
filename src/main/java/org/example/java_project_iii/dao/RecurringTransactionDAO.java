package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.RecurringTransactionPOJO;

import java.util.ArrayList;

/**
 * Interface for accessing recurring transaction data
 */
public interface RecurringTransactionDAO {

    public ArrayList<RecurringTransactionPOJO> getAllRecurringTransactions();

    public RecurringTransactionPOJO getRecurringTransaction(int recurringTransactionId);

    public void addRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO);

    public RecurringTransactionPOJO getByTransactionId(int transactionId);

    public void deleteRecurringTransaction(int recurringTransactionId);

    public void updateRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO);
}
