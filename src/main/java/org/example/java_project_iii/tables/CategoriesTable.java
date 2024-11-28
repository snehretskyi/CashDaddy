package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.CategoriesDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.CategoriesPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

public class CategoriesTable implements CategoriesDAO {


    /**
     * Singleton class for managing org.example.java_project_iii.database operations on the CategoriesTable .
     */
    private static CategoriesTable instance;
    Database db=Database.getInstance();

    private CategoriesTable() throws Exception {
        db = Database.getInstance();
    }

    public static CategoriesTable getInstance() throws Exception {
        if(instance == null){
            instance = new CategoriesTable();
        }
        return instance;
    }

    ArrayList<CategoriesPOJO> categories;

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
    public ArrayList<CategoriesPOJO> getAllCategories() {
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        categories = new ArrayList<>();
        try {
            Statement getCategories = getDb().getConnection().createStatement();
            ResultSet data = getCategories.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                categories.add(new CategoriesPOJO(
                        data.getInt(CATEGORIES_COLUMN_ID),
                        data.getString(CATEGORIES_COLUMN_CATEGORY_TYPE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public CategoriesPOJO getCategories(int id) {
        String query = "SELECT * FROM " + TABLE_CATEGORIES +
                " WHERE " + CATEGORIES_COLUMN_ID + " = " + id;
        try{
            Statement getCategory = getDb().getConnection().createStatement();
            ResultSet data = getCategory.executeQuery(query);
            if(data.next()){
                CategoriesPOJO category = new CategoriesPOJO(
                        data.getInt(CATEGORIES_COLUMN_ID),
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
