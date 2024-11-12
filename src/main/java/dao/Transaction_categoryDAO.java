package dao;

import pojo.Transaction_categoryPOJO;

import java.util.ArrayList;

public interface Transaction_categoryDAO {
    public ArrayList<Transaction_categoryPOJO> getTransaction_category();
    public Transaction_categoryPOJO getTransaction_category(int transaction_category_id);
}
