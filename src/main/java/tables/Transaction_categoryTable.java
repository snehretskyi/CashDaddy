package tables;

import dao.Transaction_categoryDAO;
import database.Database;
import pojo.Transaction_categoryPOJO;
import pojo.TransactionsPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class Transaction_categoryTable implements Transaction_categoryDAO {
    Database db;
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

    public Transaction_categoryTable() throws Exception {
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

    @Override
    public void addTransaction_category(Transaction_categoryPOJO transaction_categoryPOJO) {
        String query = "INSERT INTO " + TABLE_TRANSACTION_CATEGORY +
                "(" + TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID +
                ", " + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + ") VALUES (" +
                transaction_categoryPOJO.getTransaction_id() + ", "
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

