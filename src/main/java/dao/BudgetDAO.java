package dao;


import pojo.BudgetPOJO;

import java.util.ArrayList;

public interface BudgetDAO {
    public ArrayList<BudgetPOJO> getAllBudgets();
    public BudgetPOJO getBudget(int budget_id);
}

