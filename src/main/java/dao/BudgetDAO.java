package dao;

import pojo.BudgetPOJO;

import java.util.ArrayList;

public interface BudgetDAO {
    public ArrayList<BudgetPOJO> getAllBudgets();
    public BudgetPOJO getBudget(int budget_id);
    public void addBudget(BudgetPOJO budget);
    public void updateBudget(BudgetPOJO budget);
    public void deleteBudget(int budget_id);
}