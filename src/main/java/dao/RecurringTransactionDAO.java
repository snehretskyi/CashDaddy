package dao;

import pojo.RecurringTransactionPOJO;

import java.util.ArrayList;

public interface RecurringTransactionDAO {
    public ArrayList<RecurringTransactionPOJO> getAllRecurringTransactions();
    public RecurringTransactionPOJO getRecurringTransaction(int recurringTransactionId);
    public void addRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO);
    public RecurringTransactionPOJO getByTransactionId(int transactionId);
    public void deleteRecurringTransaction(int recurringTransactionId);
    public void updateRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO);
}
