package dao;

import pojo.RecurringTransactionPOJO;
import pojo.Transaction_categoryPOJO;

import java.util.ArrayList;

public interface RecurringTransactionDAO {
    public ArrayList<RecurringTransactionPOJO> getAllRecurringTransactions();
    public RecurringTransactionPOJO getRecurringTransaction(int recurringTransactionId);
    public void addRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO);
}
