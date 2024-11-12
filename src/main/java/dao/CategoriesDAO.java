package dao;

import pojo.CategoriesPOJO;

import java.util.ArrayList;

public interface CategoriesDAO {
    public ArrayList<CategoriesPOJO> getAllCategories();
    public CategoriesPOJO getCategories(int category_id);
}