package dao;

import pojo.Transaction_typePOJO;

import java.util.ArrayList;

public interface Transaction_typeDAO {
    public ArrayList<Transaction_typePOJO> getAllTransaction_types();
    public Transaction_typePOJO getTransaction_type(int transaction_type_id);
}