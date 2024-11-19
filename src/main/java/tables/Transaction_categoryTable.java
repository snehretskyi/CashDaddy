package tables;

import dao.Transaction_categoryDAO;
import database.Database;
import pojo.CategoriesPOJO;
import pojo.Transaction_categoryPOJO;
import pojo.TransactionsPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class Transaction_categoryTable implements Transaction_categoryDAO {


    /**
     * Singleton class for managing database operations on the Transaction_categoryTable .
     */
    private static Transaction_categoryTable instance;
    Database db=Database.getInstance();

    private Transaction_categoryTable() throws Exception {
        db = Database.getInstance();
    }

    public static Transaction_categoryTable getInstance() throws Exception {
        if(instance == null){
            instance = new Transaction_categoryTable();
        }
        return instance;
    }

    ArrayList<Transaction_categoryPOJO> transaction_categories;

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
    public ArrayList<Transaction_categoryPOJO> getAllTransaction_categories() {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_CATEGORY;
        transaction_categories = new ArrayList<>();
        try {
            Statement getCategories = getDb().getConnection().createStatement();
            ResultSet data = getCategories.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                transaction_categories.add(new Transaction_categoryPOJO(
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID),
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaction_categories;
    }

    @Override
    public Transaction_categoryPOJO getTransaction_category(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_CATEGORY +
                " WHERE " + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + " = " + id;
        System.out.println(query);
        try{
            Statement getCategory = getDb().getConnection().createStatement();
            ResultSet data = getCategory.executeQuery(query);
            if(data.next()){
                Transaction_categoryPOJO category = new Transaction_categoryPOJO(
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID),
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID)
                );
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all associated categories to transaction id
     * @param transaction_id
     * @return array list of ids
     */
    @Override
    public ArrayList<Integer> getAssociatedCategories(int transaction_id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_CATEGORY + " WHERE " +
                TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + " = " + transaction_id;
        ArrayList<Integer> associatedCategoriesIds = new ArrayList<>();
        try {
            Statement getTransaction = getDb().getConnection().createStatement();
            ResultSet data = getTransaction.executeQuery(query);
            while (data.next()) {
                // -1 because sql starts from 1, ListView from 0
                associatedCategoriesIds.add(data.getInt(TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID) - 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return associatedCategoriesIds;
    }

    /**
     * Instead of doing the usual update, removes all ids associated with transaction. Then recreates them.
     * @param transaction_categoryPOJOArrayList
     */
    @Override
    public void updateTransactionCategory(ArrayList<Transaction_categoryPOJO> transaction_categoryPOJOArrayList) {
        // id of transactions should be the same for all elements
        int transaction_id = transaction_categoryPOJOArrayList.get(0).getId();
        String deleteQuery = "DELETE FROM " + TABLE_TRANSACTION_CATEGORY +
                " WHERE " + TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + " = " + transaction_id + ";";

        try {
            Statement updateItem = getDb().getConnection().createStatement();
            updateItem.execute(deleteQuery);
            System.out.println("Associated categories cleared!");

            transaction_categoryPOJOArrayList.forEach((Transaction_categoryPOJO transaction_categoryPOJO) -> {
                this.addTransaction_category(transaction_categoryPOJO);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTransaction_category(Transaction_categoryPOJO transaction_categoryPOJO) {
        String query = "INSERT INTO " + TABLE_TRANSACTION_CATEGORY +
                "(" + TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID +
                ", " + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + ") VALUES (" +
                transaction_categoryPOJO.getId() + ", "
                + transaction_categoryPOJO.getCategory_id() +
                ")";
        try {
            getDb().getConnection().createStatement().execute(query);
            System.out.println("Inserted Record");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

