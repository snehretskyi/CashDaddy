package dao;

import pojo.TransactionsPOJO;

import java.util.ArrayList;

public interface TransactionsDAO {
    public ArrayList<TransactionsPOJO> getAllTransactions();
    public TransactionsPOJO getTransaction(int transaction_id);
    public void addTransaction(TransactionsPOJO transaction);
    public void updateTransaction(TransactionsPOJO transaction);
    public void deleteTransaction(int transaction_id);
}
