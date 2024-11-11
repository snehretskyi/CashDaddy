package dao;

import pojo.Transaction_categoryPOJO;

import java.util.ArrayList;

public interface Transaction_categoryDAO {
    public ArrayList<Transaction_categoryPOJO> getTransaction_category();
    public Transaction_categoryPOJO getTransaction_category(int transaction_category_id);
    public void addTransaction_category(Transaction_categoryPOJO transaction_category);
    public void updateTransaction_category(Transaction_categoryPOJO transaction_category);
    public void deleteTransaction_category(int transaction_category_id);
}
