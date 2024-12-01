package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.TransactionsDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.DisplayTransaction;
import org.example.java_project_iii.pojo.TransactionsPOJO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.example.java_project_iii.database.DBConst.*;

public class TransactionsTable implements TransactionsDAO {


    /**
     * Singleton class for managing org.example.java_project_iii.database operations on the TransactionsTable .
     */
    private static TransactionsTable instance;
    Database db=Database.getInstance();

    private TransactionsTable() throws Exception {
        db = Database.getInstance();
    }

    public static TransactionsTable getInstance() throws Exception {
        if(instance == null){
            instance = new TransactionsTable();
        }
        return instance;
    }

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
                        data.getInt(TRANSACTIONS_COLUMN_CATEGORY_ID),
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
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTIONS_COLUMN_ID + " = " + transaction_id;
        TransactionsPOJO transaction = new TransactionsPOJO();
        try {
            Statement getTransaction = getDb().getConnection().createStatement();
            ResultSet data = getTransaction.executeQuery(query);
            data.next();
            transaction = new TransactionsPOJO(data.getInt(TRANSACTIONS_COLUMN_ID),
                    data.getInt(TRANSACTIONS_COLUMN_ACCOUNT_ID),
                    data.getDouble(TRANSACTIONS_COLUMN_AMOUNT),
                    data.getInt(TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID),
                    data.getInt(TRANSACTIONS_COLUMN_CATEGORY_ID),
                    data.getDate(TRANSACTIONS_COLUMN_TRANSACTION_DATE),
                    data.getString(TRANSACTIONS_COLUMN_DESCRIPTION));
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
                TRANSACTIONS_COLUMN_ACCOUNT_ID + ", " +
                TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + "," +
                TRANSACTIONS_COLUMN_CATEGORY_ID + "," +
                TRANSACTIONS_COLUMN_TRANSACTION_DATE + "," +
                TRANSACTIONS_COLUMN_DESCRIPTION + ") VALUES ('" +
                transactions.getAmount() + "','" + transactions.getTransaction_account_id() + "','" + transactions.getTransaction_type_id() + "','" +
                + transactions.getTransaction_category_id() + "','" + transactions.getTransaction_date() + "','" + transactions.getTransaction_description() +
                "')";

                String updateBalanceQuery = "UPDATE " + TABLE_ACCOUNTS + " SET " + ACCOUNTS_COLUMN_BALANCE +
                        " = " + ACCOUNTS_COLUMN_BALANCE + " + " + transactions.getAmount() +
                        " WHERE " + ACCOUNTS_COLUMN_ID + " = " + transactions.getTransaction_account_id();

        try {
            getDb().getConnection().createStatement().execute(query);
            getDb().getConnection().createStatement().executeUpdate(updateBalanceQuery);

            // get automatically generated id, update org.example.java_project_iii.pojo with it
            Statement getAutoId = getDb().getConnection().createStatement();
            ResultSet resultSet =  getAutoId.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                // get first result
                int generatedId = resultSet.getInt(1);
                transactions.setId(generatedId);
            }
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
                TRANSACTIONS_COLUMN_ACCOUNT_ID + " = " + transactions.getTransaction_account_id() + "," +
                TRANSACTIONS_COLUMN_AMOUNT + " = " + transactions.getAmount() + "," +
                TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + " = " + transactions.getTransaction_type_id() + "," +
                TRANSACTIONS_COLUMN_CATEGORY_ID + " = " + transactions.getTransaction_category_id() + "," +
                TRANSACTIONS_COLUMN_DESCRIPTION + " = '" + transactions.getTransaction_description() + "'" + "," +
                TRANSACTIONS_COLUMN_TRANSACTION_DATE + " = '" + transactions.getTransaction_date() + "'" +
                " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + transactions.getId();

        String reverseOldAmountQuery = "UPDATE " + TABLE_ACCOUNTS + " SET " + ACCOUNTS_COLUMN_BALANCE +
                " = " + ACCOUNTS_COLUMN_BALANCE + " - (SELECT " + TRANSACTIONS_COLUMN_AMOUNT +
                " FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + transactions.getId() + ")" +
                " WHERE " + ACCOUNTS_COLUMN_ID + " = " + transactions.getTransaction_account_id();

        String applyNewAmountQuery = "UPDATE " + TABLE_ACCOUNTS + " SET " + ACCOUNTS_COLUMN_BALANCE +
                " = " + ACCOUNTS_COLUMN_BALANCE + " + " + transactions.getAmount() +
                " WHERE " + ACCOUNTS_COLUMN_ID + " = " + transactions.getTransaction_account_id();

        try {
            Statement updateItem = getDb().getConnection().createStatement();
            getDb().getConnection().createStatement().executeUpdate(reverseOldAmountQuery);
            getDb().getConnection().createStatement().executeUpdate(applyNewAmountQuery);

            System.out.println("Updated Transaction!");
            updateItem.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes a transaction and its related records from the transaction_category table.
     *
     * @param id The ID of the transaction to be deleted.
     */

    @Override
    public void deleteTransaction(int id) {
        String deleteFromRecurringTransaction = "DELETE FROM recurring_transaction WHERE transaction_id = ?";
        String deleteFromTransaction = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTIONS_COLUMN_ID + " = ?";

        String adjustBalanceQuery = "UPDATE " + TABLE_ACCOUNTS + " SET " + ACCOUNTS_COLUMN_BALANCE +
                " = " + ACCOUNTS_COLUMN_BALANCE + " - (SELECT " + TRANSACTIONS_COLUMN_AMOUNT +
                " FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + id + ")" +
                " WHERE " + ACCOUNTS_COLUMN_ID + " = (SELECT " + TRANSACTIONS_COLUMN_ACCOUNT_ID +
                " FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + id + ")";

        try {
            getDb().getConnection().createStatement().executeUpdate(adjustBalanceQuery);
            PreparedStatement stmt1 = getDb().getConnection().prepareStatement(deleteFromRecurringTransaction);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            System.out.println("Deleted record from recurring_transaction");

            PreparedStatement stmt2 = getDb().getConnection().prepareStatement(deleteFromTransaction);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
            System.out.println("Deleted record from transactions");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public ArrayList<DisplayTransaction> getDetailedTransaction(){
        ArrayList<DisplayTransaction> transactions = new ArrayList<DisplayTransaction>();

        String query = "SELECT " +
                "transactions.transaction_id AS id, " +
                "accounts.account_type AS account_name, " +
                "transactions.amount, " +
                "transaction_types.type AS transaction_type_name, " +
                "categories.category_type AS category_type_name, " +
                "transactions.transaction_date, " +
                "transactions.description, " +
                "CASE WHEN recurring_transaction.interval_days IS NOT NULL THEN 'Yes' ELSE 'No' END AS recurring_status, " +
                "COALESCE(recurring_transaction.interval_days, 'N/A') AS interval_days " +
                "FROM transactions " +
                "JOIN accounts ON transactions.account_id = accounts.account_id " +
                "JOIN transaction_types ON transactions.transaction_type_id = transaction_types.transaction_type_id " +
                "JOIN categories ON transactions.category_id = categories.category_id " +
                "LEFT JOIN recurring_transaction ON transactions.transaction_id = recurring_transaction.transaction_id " +
                "ORDER BY transactions.transaction_id ASC";


        try {
            Statement getTransactions = db.getConnection().createStatement();
            ResultSet data = getTransactions.executeQuery(query);

            // Process each row in the ResultSet
            while (data.next()) {
                transactions.add(new DisplayTransaction(
                        data.getInt("id"),
                        data.getString("account_name"),
                        data.getString("transaction_type_name"),
                        data.getString("category_type_name"),
                        data.getDouble("amount"),
                        data.getDate("transaction_date"),
                        data.getString("description"),
                        data.getString("recurring_status"),
                        data.getString("interval_days")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }


    public TransactionsPOJO getTransactionById(int transactionId) throws Exception {
        for (TransactionsPOJO transaction : getAllTransactions()) {
            if (transaction.getId() == transactionId) {
                return transaction;
            }
        }
        throw new Exception("Transaction not found for ID: " + transactionId);
    }

}