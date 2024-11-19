package dao;


import pojo.Transaction_categoryPOJO;
import pojo.TransactionsPOJO;

import java.util.ArrayList;

public interface Transaction_categoryDAO {
    public ArrayList<Transaction_categoryPOJO> getAllTransaction_categories();
    public Transaction_categoryPOJO getTransaction_category(int transaction_category_id);
    public void addTransaction_category(Transaction_categoryPOJO transaction_categoryPOJO);
    public ArrayList<Integer> getAssociatedCategories(int transaction_id);
    public void updateTransactionCategory(ArrayList<Transaction_categoryPOJO> transaction_categoryPOJOArrayList);
}
