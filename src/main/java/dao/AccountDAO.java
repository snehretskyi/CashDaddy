package dao;

import pojo.AccountPOJO;

import java.util.ArrayList;

public interface AccountDAO {
    public ArrayList<AccountPOJO> getAllAccounts();
    public AccountPOJO getAccount(int account_id);
}
