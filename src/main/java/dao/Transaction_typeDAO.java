package dao;

import pojo.Transaction_typePOJO;

import java.util.ArrayList;

public interface Transaction_typeDAO {
    public ArrayList<Transaction_typePOJO> getAllTransaction_types();
    public Transaction_typePOJO getTransaction_type(int transaction_type_id);
    public void addTransaction_type(Transaction_typePOJO transaction_type);
    public void updateTransaction_type(Transaction_typePOJO transaction_type);
    public void deleteTransaction_type(int transaction_type_id);
}