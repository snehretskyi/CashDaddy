package tables;

import dao.TransactionsDAO;
import database.Database;
import pojo.TransactionsPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class TransactionsTable implements TransactionsDAO {
    Database db;
    ArrayList<TransactionsPOJO> transactions;

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

    public TransactionsTable() throws Exception {
    }

    @Override
    public ArrayList<TransactionsPOJO> getAllTransactions() {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;
        transactions = new ArrayList<TransactionsPOJO>();
        try {
            Statement getItems = getDb().getConnection().createStatement();
            ResultSet data = getItems.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while(data.next()) {
                transactions.add(new TransactionsPOJO(data.getInt(TRANSACTIONS_COLUMN_ID),
                        data.getInt(TRANSACTIONS_COLUMN_ACCOUNT_ID),
                        data.getDouble(TRANSACTIONS_COLUMN_AMOUNT),
                        data.getInt(TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID),
                        data.getDate(TRANSACTIONS_COLUMN_TRANSACTION_DATE),
                        data.getString(TRANSACTIONS_COLUMN_DESCRIPTION)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public TransactionsPOJO getTransaction(int transaction_id) {
        String query = "SELECT FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTIONS_COLUMN_ID + " = " + transaction_id;
        TransactionsPOJO transaction = new TransactionsPOJO();
        try {
            Statement getTransaction = getDb().getConnection().createStatement();
            ResultSet data = getTransaction.executeQuery(query);
            data.next();
            transactions.add(new TransactionsPOJO(data.getInt(TRANSACTIONS_COLUMN_ID),
                    data.getInt(TRANSACTIONS_COLUMN_ACCOUNT_ID),
                    data.getDouble(TRANSACTIONS_COLUMN_AMOUNT),
                    data.getInt(TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID),
                    data.getDate(TRANSACTIONS_COLUMN_TRANSACTION_DATE),
                    data.getString(TRANSACTIONS_COLUMN_DESCRIPTION)));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    @Override
    public void addTransaction(TransactionsPOJO transactions) {
        String query = "INSERT INTO " + TABLE_TRANSACTIONS +
                "(" + TRANSACTIONS_COLUMN_AMOUNT + ", " +
                TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + "," +
                TRANSACTIONS_COLUMN_TRANSACTION_DATE + "," +
                TRANSACTIONS_COLUMN_DESCRIPTION + ") VALUES ('" +
                transactions.getAmount() + "','" + transactions.getTransaction_type_id() + "','" +
                transactions.getTransaction_date() + "','" + transactions.getTransaction_description() +
                "')";
        try {
            getDb().getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTransaction(TransactionsPOJO transactions) {
        String query = "UPDATE " + TABLE_TRANSACTIONS + " SET " +
                TRANSACTIONS_COLUMN_ACCOUNT_ID + " " + transactions.getTransaction_account_id() +  "," +
                TRANSACTIONS_COLUMN_AMOUNT + " " + transactions.getAmount() + "," +
                TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + " " + transactions.getTransaction_type_id() + "," +
                TRANSACTIONS_COLUMN_DESCRIPTION + " " + transactions.getTransaction_description() +
                " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + transactions.getTransaction_id();
        try {
            Statement updateItem = getDb().getConnection().createStatement();
            updateItem.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTransaction(int id) {
        String query  = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTIONS_COLUMN_ID + " = " + id;
        try {
            getDb().getConnection().createStatement().execute(query);
            System.out.println("Deleted record");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}