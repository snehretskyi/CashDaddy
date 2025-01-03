package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.AccountDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.AccountPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

public class AccountsTable implements AccountDAO {

    /**
     * Singleton class for managing org.example.java_project_iii.database operations on the AccountsTable .
     */
    private static AccountsTable instance;
    Database db = Database.getInstance();

    private AccountsTable() throws Exception {
        db = Database.getInstance();
    }

    public static AccountsTable getInstance() throws Exception {
        if (instance == null) {
            instance = new AccountsTable();
        }
        return instance;
    }

    ArrayList<AccountPOJO> accounts;

    /**
     * Try to get db, if no credentials are entered the program won't crash.
     *
     * @return
     * @throws Exception
     */
    public Database getDb() throws Exception {
        try {
            if (db == null) {
                db = Database.getInstance();
            }
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public ArrayList<AccountPOJO> getAllAccounts() {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS;
        accounts = new ArrayList<AccountPOJO>();
        try {
            Statement getItems = getDb().getConnection().createStatement();
            ResultSet data = getItems.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                accounts.add(new AccountPOJO(data.getInt(ACCOUNTS_COLUMN_ID),
                        data.getString(ACCOUNTS_COLUMN_ACCOUNT_TYPE),
                        data.getDouble(ACCOUNTS_COLUMN_BALANCE),
                        data.getString(ACCOUNTS_COLUMN_INFORMATION)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public AccountPOJO getAccount(int id) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS +
                " WHERE " + ACCOUNTS_COLUMN_ID + " = " + id;
        try {
            Statement getAccount = db.getConnection().createStatement();
            ResultSet data = getAccount.executeQuery(query);
            if (data.next()) {
                AccountPOJO account = new AccountPOJO(
                        data.getInt(ACCOUNTS_COLUMN_ID),
                        data.getString(ACCOUNTS_COLUMN_ACCOUNT_TYPE),
                        data.getDouble(ACCOUNTS_COLUMN_BALANCE),
                        data.getString(ACCOUNTS_COLUMN_INFORMATION)
                );
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}