package tables;

import dao.Transaction_categoryDAO;
import database.Database;
import pojo.Transaction_categoryPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class Transaction_categoryTable implements Transaction_categoryDAO {
    Database db = Database.getInstance();
    ArrayList<Transaction_categoryPOJO> transaction_categories;

    public Transaction_categoryTable() throws Exception {
    }

    @Override
    public ArrayList<Transaction_categoryPOJO> getAllTransaction_categories() {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_CATEGORY;
        transaction_categories = new ArrayList<>();
        try {
            Statement getCategories = db.getConnection().createStatement();
            ResultSet data = getCategories.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                transaction_categories.add(new Transaction_categoryPOJO(
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID),
                        data.getInt(TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction_categories;
    }

    @Override
    public Transaction_categoryPOJO getTransaction_category(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_CATEGORY +
                " WHERE " + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + " = " + id;
        try{
            Statement getCategory = db.getConnection().createStatement();
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
}
