package org.example.java_project_iii.dao;

import org.example.java_project_iii.pojo.BudgetPOJO;

import java.util.ArrayList;

/**
 * Interface for accessing budget data
 */
public interface BudgetDAO {

    public ArrayList<BudgetPOJO> getAllBudgets();

    public BudgetPOJO getBudget(int budget_id);
}

