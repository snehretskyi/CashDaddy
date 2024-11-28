package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.AccountPOJO;

import java.util.ArrayList;

public interface AccountDAO {
    public ArrayList<AccountPOJO> getAllAccounts();
    public AccountPOJO getAccount(int account_id);

}
