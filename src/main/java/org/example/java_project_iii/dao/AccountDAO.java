package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.AccountPOJO;

import java.util.ArrayList;

/**
 * Interface for accessing account data
 */
public interface AccountDAO {
    //Retrieves all accounts from the database
    public ArrayList<AccountPOJO> getAllAccounts();

    //Retrieves a specific account by its id
    public AccountPOJO getAccount(int account_id);

}
