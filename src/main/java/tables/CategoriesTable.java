package tables;

import dao.CategoriesDAO;
import database.Database;
import pojo.CategoriesPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class CategoriesTable implements CategoriesDAO {
    Database db = Database.getInstance();
    ArrayList<CategoriesPOJO> categories;

    public CategoriesTable() throws Exception {
    }

    @Override
    public ArrayList<CategoriesPOJO> getAllCategories() {
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        categories = new ArrayList<CategoriesPOJO>();
        try {
            Statement getCategories = db.getConnection().createStatement();
            ResultSet data = getCategories.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                categories.add(new CategoriesPOJO(
                        data.getInt(CATEGORIES_COLUMN_ID),
                        data.getInt(CATEGORIES_COLUMN_BUDGET_ID),
                        data.getString(CATEGORIES_COLUMN_CATEGORY_TYPE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public CategoriesPOJO getCategories(int id) {
        String query = "SELECT * FROM " + TABLE_CATEGORIES +
                " WHERE " + CATEGORIES_COLUMN_ID + " = " + id;
        try{
            Statement getCategory = db.getConnection().createStatement();
            ResultSet data = getCategory.executeQuery(query);
            if(data.next()){
                CategoriesPOJO category = new CategoriesPOJO(
                        data.getInt(CATEGORIES_COLUMN_ID),
                        data.getInt(CATEGORIES_COLUMN_BUDGET_ID),
                        data.getString(CATEGORIES_COLUMN_CATEGORY_TYPE)
                );
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
