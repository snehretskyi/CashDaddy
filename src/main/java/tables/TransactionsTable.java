package tables;

import dao.TransactionsDAO;
import database.Database;
import pojo.CategoriesPOJO;
import pojo.DisplayTransaction;
import pojo.Transaction_categoryPOJO;
import pojo.TransactionsPOJO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class TransactionsTable implements TransactionsDAO {


    /**
     * Singleton class for managing database operations on the TransactionsTable .
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
                TRANSACTIONS_COLUMN_TRANSACTION_DATE + "," +
                TRANSACTIONS_COLUMN_DESCRIPTION + ") VALUES ('" +
                transactions.getAmount() + "','" + transactions.getTransaction_account_id() + "','" + transactions.getTransaction_type_id() + "','" +
                transactions.getTransaction_date() + "','" + transactions.getTransaction_description() +
                "')";
        try {
            getDb().getConnection().createStatement().execute(query);

            // get automatically generated id, update pojo with it
            Statement getAutoId = getDb().getConnection().createStatement();
            ResultSet resultSet =  getAutoId.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                // get first result
                int generatedId = resultSet.getInt(1);
                System.out.println(generatedId);
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
                TRANSACTIONS_COLUMN_DESCRIPTION + " = '" + transactions.getTransaction_description() + "'" +
                " WHERE " + TRANSACTIONS_COLUMN_ID + " = " + transactions.getId();
        System.out.println(query);
        try {
            Statement updateItem = getDb().getConnection().createStatement();
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
        String deleteFromCategory = "DELETE FROM " + TABLE_TRANSACTION_CATEGORY + " WHERE " +
                TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + " = ?";
        String deleteFromTransaction = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANSACTIONS_COLUMN_ID + " = ?";

        try {
            // Delete related records in transaction_category first
            PreparedStatement stmt1 = getDb().getConnection().prepareStatement(deleteFromCategory);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            System.out.println("Deleted related records in transaction_category");

            // Now, delete the record from transactions
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

        String query = "SELECT transactions.transaction_id AS id, " +
                "accounts.account_type AS account_name, " +
                "transactions.amount, " +
                TABLE_TRANSACTION_TYPES + "." + TRANSACTION_TYPES_COLUMN_TYPE + " AS transaction_type_name, " +
                "transactions.transaction_date, " +
                "transactions.description " +
                "FROM transactions " +
                "JOIN accounts ON transactions.account_id = accounts.account_id " +
                "JOIN transaction_types ON transactions.transaction_type_id = transaction_types.transaction_type_id " +
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
                        data.getString("amount"),
                        data.getString("transaction_date"),
                        data.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }


}