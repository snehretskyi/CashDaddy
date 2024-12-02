package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.BudgetDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.BudgetPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

/**
 * Singleton class for managing database operations on the Budget table
 */

public class BudgetTable implements BudgetDAO {

    private static BudgetTable instance;
    Database db = Database.getInstance();

    private BudgetTable() throws Exception {
        db = Database.getInstance();
    }

    /**
     * Get the single instance of BudgetTable.
     *
     * @return the single instance of BudgetTable.
     * @throws Exception if there is an error.
     */
    public static BudgetTable getInstance() throws Exception {
        if (instance == null) {
            instance = new BudgetTable();
        }
        return instance;
    }

    ArrayList<BudgetPOJO> budgets;

    /**
     * Get the database instance.
     *
     * @return the database instance.
     * @throws Exception if there is an error.
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
     * Get all budget records
     *
     * @return a list of all budget records
     */

    @Override
    public ArrayList<BudgetPOJO> getAllBudgets() {
        String query = "SELECT * FROM " + TABLE_BUDGETS;
        budgets = new ArrayList<BudgetPOJO>();
        try {
            Statement getBudgets = getDb().getConnection().createStatement();
            ResultSet data = getBudgets.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                budgets.add(new BudgetPOJO(
                        data.getInt(BUDGETS_COLUMN_ID),
                        data.getInt(BUDGETS_COLUMN_TRANSACTION_ID),
                        data.getDouble(BUDGETS_COLUMN_GOAL_AMOUNT),
                        data.getDate(BUDGETS_COLUMN_START_DATE),
                        data.getDate(BUDGETS_COLUMN_END_DATE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return budgets;
    }

    /**
     * Get a budget record by its ID
     *
     * @param id id the budget ID
     * @return the budget record
     */
    @Override
    public BudgetPOJO getBudget(int id) {
        String query = "SELECT * FROM " + TABLE_BUDGETS +
                " WHERE " + BUDGETS_COLUMN_ID + " = " + id;
        try {
            Statement getBudget = getDb().getConnection().createStatement();
            ResultSet data = getBudget.executeQuery(query);
            if (data.next()) {
                BudgetPOJO budget = new BudgetPOJO(
                        data.getInt(BUDGETS_COLUMN_ID),
                        data.getInt(BUDGETS_COLUMN_TRANSACTION_ID),
                        data.getDouble(BUDGETS_COLUMN_GOAL_AMOUNT),
                        data.getDate(BUDGETS_COLUMN_START_DATE),
                        data.getDate(BUDGETS_COLUMN_END_DATE)
                );
                return budget;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert a new budget record
     *
     * @param budget budget the budget to insert
     */
    public void insertBudget(BudgetPOJO budget) {
        String query = "INSERT INTO " + TABLE_BUDGETS +
                "(" + BUDGETS_COLUMN_TRANSACTION_ID + ", " +
                BUDGETS_COLUMN_GOAL_AMOUNT + ", " +
                BUDGETS_COLUMN_START_DATE + ", " +
                BUDGETS_COLUMN_END_DATE + ") VALUES ('" +
                budget.getTransaction_id() + "', '" +
                budget.getGoal_amount() + "', '" +
                budget.getStart_date() + "', '" +
                budget.getEnd_date() + "')";
        try {
            db.getConnection().createStatement().execute(query);
            System.out.println("Budget added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while adding budget.");
        }

    }

    /**
     * Remove a budget record by its ID
     *
     * @param id id the budget ID
     */
    public void removeBudget(int id) {
        String query = "DELETE FROM " + TABLE_BUDGETS + " WHERE " + BUDGETS_COLUMN_ID + " = " + id;
        try {
            db.getConnection().createStatement().execute(query);
            System.out.println("Budget record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while deleting budget record.");
        }
    }

}
