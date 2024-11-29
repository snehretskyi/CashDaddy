package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.BudgetDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.BudgetPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

public class BudgetTable implements BudgetDAO {
    /**
     * Singleton class for managing org.example.java_project_iii.database operations on the Budget table.
     */
    private static BudgetTable instance;
    Database db=Database.getInstance();

    private BudgetTable() throws Exception {
        db = Database.getInstance();
    }

    public static BudgetTable getInstance() throws Exception {
        if(instance == null){
            instance = new BudgetTable();
        }
        return instance;
    }

    ArrayList<BudgetPOJO> budgets;

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

    @Override
    public BudgetPOJO getBudget(int id) {
        String query = "SELECT * FROM " + TABLE_BUDGETS +
                " WHERE " + BUDGETS_COLUMN_ID + " = " + id;
        try{
            Statement getBudget = getDb().getConnection().createStatement();
            ResultSet data = getBudget.executeQuery(query);
            if(data.next()){
                BudgetPOJO budget = new BudgetPOJO(
                        data.getInt(BUDGETS_COLUMN_ID),
                        data.getInt(BUDGETS_COLUMN_TRANSACTION_ID),
                        data.getDouble(BUDGETS_COLUMN_GOAL_AMOUNT),
                        data.getDate(BUDGETS_COLUMN_START_DATE),
                        data.getDate(BUDGETS_COLUMN_END_DATE)
                );
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void insertBudget(BudgetPOJO budget) {
    }

    public void addBudget(BudgetPOJO newBudget) {
    }
}
