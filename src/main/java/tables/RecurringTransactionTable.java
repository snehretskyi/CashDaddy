package tables;

import dao.RecurringTransactionDAO;
import database.Database;
import pojo.CategoriesPOJO;
import pojo.RecurringTransactionPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class RecurringTransactionTable implements RecurringTransactionDAO {
    Database db;
    ArrayList<RecurringTransactionPOJO> recurringTransactions;

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

    /**
     * Gets all recurring transactions from db
     * @return recurringTransactions array list of recurring transactions
     */
    @Override
    public ArrayList<RecurringTransactionPOJO> getAllRecurringTransactions() {
        String query = "SELECT * FROM " + TABLE_RECURRING_TRANSACTION;
        recurringTransactions = new ArrayList<>();
        try {
            Statement getRecurring = getDb().getConnection().createStatement();
            ResultSet data = getRecurring.executeQuery(query);
            while(data.next()) {
                recurringTransactions.add(new RecurringTransactionPOJO(data.getInt(RECURRING_TRANSACTION_COLUMN_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS)));
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
     * @return recurringTransaction pojo of a recurring transaction
     */
    @Override
    public RecurringTransactionPOJO getRecurringTransaction(int recurringTransactionId) {
        String query = "SELECT * FROM " + TABLE_RECURRING_TRANSACTION +
                " WHERE " + RECURRING_TRANSACTION_COLUMN_ID + " = " + recurringTransactionId;
        try{
            Statement getRecurringTransaction = getDb().getConnection().createStatement();
            ResultSet data = getRecurringTransaction.executeQuery(query);
            if(data.next()){
                RecurringTransactionPOJO recurringTransaction = new RecurringTransactionPOJO(
                        data.getInt(RECURRING_TRANSACTION_COLUMN_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID),
                        data.getInt(RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS)
                );
                return recurringTransaction;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds one recurring transaction, updates its POJO with auto generated id from MariaDB
     * @param recurringTransactionPOJO the recurring transaction to be added
     */
    @Override
    public void addRecurringTransaction(RecurringTransactionPOJO recurringTransactionPOJO) {
        String query = "INSERT INTO " + TABLE_RECURRING_TRANSACTION +
                "(" + RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + ", " +
                RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS + "," + ") VALUES (" +
                recurringTransactionPOJO.getTransactionId() + "," + recurringTransactionPOJO.getIntervalDays()
                + ")";
        try {
            getDb().getConnection().createStatement().execute(query);

            // get automatically generated id, update pojo with it
            Statement getAutoId = getDb().getConnection().createStatement();
            ResultSet resultSet =  getAutoId.executeQuery("SELECT LAST_INSERT_ID()");
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

}
