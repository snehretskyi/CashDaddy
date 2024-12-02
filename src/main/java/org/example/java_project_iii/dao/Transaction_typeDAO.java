package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.TransactionTypePOJO;

import java.util.ArrayList;

/**
 * Interface for accessing transaction type data
 */
public interface Transaction_typeDAO {

    public ArrayList<TransactionTypePOJO> getAllTransaction_types();

    public TransactionTypePOJO getTransaction_type(int transaction_type_id);
}