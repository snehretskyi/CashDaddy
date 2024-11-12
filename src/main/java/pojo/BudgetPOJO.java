package pojo;

import java.util.Date;

public class BudgetPOJO {
    private int budget_id;
    private int category_id;
    private double goal_amount;
    private Date start_date;
    private Date end_date;

    public BudgetPOJO(int budget_id, int category_id, double goal_amount, Date start_date, Date end_date) {
        this.budget_id = budget_id;
        this.category_id = category_id;
        this.goal_amount = goal_amount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getBudget_idd() {
        return budget_id;
    }

    public void setId(int budget_id) {
        this.budget_id = budget_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getGoal_amount() {
        return goal_amount;
    }

    public void setGoal_amount(double goal_amount) {
        this.goal_amount = goal_amount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }



    //TODO after Riddhi adds budget_name in Database public String toString() {return budget_name;}
}
