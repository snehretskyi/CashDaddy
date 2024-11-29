package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.CategoriesPOJO;

import java.util.ArrayList;

public interface CategoriesDAO {
    public ArrayList<CategoriesPOJO> getAllCategories();
    public CategoriesPOJO getCategories(int category_id);
}