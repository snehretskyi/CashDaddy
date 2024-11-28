package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.TransactionsPOJO;

import java.util.ArrayList;

public interface TransactionsDAO {
    public ArrayList<TransactionsPOJO> getAllTransactions();
    public TransactionsPOJO getTransaction(int transaction_id);
    public void addTransaction(TransactionsPOJO transaction);
    public void updateTransaction(TransactionsPOJO transaction);
    public void deleteTransaction(int transaction_id);
}
