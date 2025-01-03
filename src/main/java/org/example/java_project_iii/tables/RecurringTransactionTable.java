package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.RecurringTransactionDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.RecurringTransactionPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

public class RecurringTransactionTable implements RecurringTransactionDAO {
    private Database db;
    private static RecurringTransactionTable instance;

    private RecurringTransactionTable() throws Exception {
        db = Database.getInstance();
    }

    /**
     * @return instance of the table
     * @throws Exception
     */
    public static RecurringTransactionTable getInstance() throws Exception {
        if (instance == null) {
            instance = new RecurringTransactionTable();
        }
        return instance;
    }

    ArrayList<RecurringTransactionPOJO> recurringTransactions;

    /**
     * @return instance of db
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

    /**
     * Gets all recurring transactions from db
     *
     * @return recurringTransactions array list of recurring transactions
     */
    @Override
    public ArrayList<RecurringTransactionPOJO> getAllRecurringTransactions() {
        String query = "SELECT * FROM " + TABLE_RECURRING_TRANSACTION;
        recurringTransactions = new ArrayList<>();
        try {
            Statement getRecurring = getDb().getConnection().createStatement();
            ResultSet data = getRecurring.executeQuery(query);
            while (data.next()) {
                recurringTransactions.add(new RecurringTransactionPOJO(data.getInt(RECURRING_TRANSACTION_COLUMN_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS),
                        data.getDate(RECURRING_TRANSACTION_NEXT_DATE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return recurringTransactions;
    }

    /**
     * @param recurringTransactionId id of a recurring transaction
     * @return recurringTransaction org.example.java_project_iii.pojo of a recurring transaction
     */
    @Override
    public RecurringTransactionPOJO getRecurringTransaction(int recurringTransactionId) {
        String query = "SELECT * FROM " + TABLE_RECURRING_TRANSACTION +
                " WHERE " + RECURRING_TRANSACTION_COLUMN_ID + " = " + recurringTransactionId;
        try {
            Statement getRecurringTransaction = getDb().getConnection().createStatement();
            ResultSet data = getRecurringTransaction.executeQuery(query);
            if (data.next()) {
                RecurringTransactionPOJO recurringTransaction = new RecurringTransactionPOJO(
                        data.getInt(RECURRING_TRANSACTION_COLUMN_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS),
                        data.getDate(RECURRING_TRANSACTION_NEXT_DATE)
                );
                return recurringTransaction;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds one recurring transaction, updates its POJO with auto generated id from MariaDB
     *
     * @param recurringTransactionPOJO the recurring transaction to be added
     */
    @Override
    public void addRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO) {
        String query = "INSERT INTO " + TABLE_RECURRING_TRANSACTION +
                "(" + RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + ", " +
                RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS + ", " +
                RECURRING_TRANSACTION_NEXT_DATE + ") VALUES (" +
                recurringTransactionPOJO.getTransactionId() + ","
                + recurringTransactionPOJO.getIntervalDays() + ", '"
                + recurringTransactionPOJO.getNextDate()
                + "')";
        try {
            getDb().getConnection().createStatement().execute(query);

            // get automatically generated id, update org.example.java_project_iii.pojo with it
            Statement getAutoId = getDb().getConnection().createStatement();
            ResultSet resultSet = getAutoId.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                // get first result
                int generatedId = resultSet.getInt(1);
                System.out.println(generatedId);
                recurringTransactionPOJO.setId(generatedId);
            }
            System.out.println("Inserted Record");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RecurringTransactionPOJO getByTransactionId(int transactionId) {
        String query = "SELECT * FROM " + TABLE_RECURRING_TRANSACTION +
                " WHERE " + RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + " = " + transactionId;
        try {
            Statement getRecurringTransaction = getDb().getConnection().createStatement();
            ResultSet data = getRecurringTransaction.executeQuery(query);
            if (data.next()) {
                RecurringTransactionPOJO recurringTransaction = new RecurringTransactionPOJO(
                        data.getInt(RECURRING_TRANSACTION_COLUMN_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS),
                        data.getDate(RECURRING_TRANSACTION_NEXT_DATE)
                );
                return recurringTransaction;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param recurringTransactionId
     */
    @Override
    public void deleteRecurringTransaction(int recurringTransactionId) {
        String query = "DELETE FROM " + TABLE_RECURRING_TRANSACTION + " WHERE " +
                RECURRING_TRANSACTION_COLUMN_ID + " = " + recurringTransactionId;
        try {
            getDb().getConnection().createStatement().execute(query);
            System.out.println("Deleted record");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update recurring transaction with value from pojo
     *
     * @param recurringTransactionPOJO
     */
    @Override
    public void updateRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO) {
        String query = "UPDATE " + TABLE_RECURRING_TRANSACTION + " SET " +
                RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + " = " + recurringTransactionPOJO.getTransactionId() + ", " +
                RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS + " = " + recurringTransactionPOJO.getIntervalDays() + ", " +
                RECURRING_TRANSACTION_NEXT_DATE + " = '" + recurringTransactionPOJO.getNextDate() + "'" +
                " WHERE " + RECURRING_TRANSACTION_COLUMN_ID + " = " + recurringTransactionPOJO.getId();
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

}
