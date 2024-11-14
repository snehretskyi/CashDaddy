package tables;

import dao.AccountDAO;
import database.Database;
import pojo.AccountPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class AccountsTable implements AccountDAO {
    Database db;
    ArrayList<AccountPOJO> accounts;

    /**
     * Try to get db, if no credentials are entered the program won't crash.
     * @return
     * @throws Exception
     */
    public Database getDb() throws Exception {
        try {
            if(db == null){
                db = Database.getInstance();
            }
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public AccountsTable() throws Exception {
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
        try{
            Statement getAccount = getDb().getConnection().createStatement();
            ResultSet data = getAccount.executeQuery(query);
            if(data.next()){
                AccountPOJO account = new AccountPOJO(
                        data.getInt(ACCOUNTS_COLUMN_ID),
                        data.getString(ACCOUNTS_COLUMN_ACCOUNT_TYPE),
                        data.getDouble(ACCOUNTS_COLUMN_BALANCE),
                        data.getString(ACCOUNTS_COLUMN_INFORMATION)
                );
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}