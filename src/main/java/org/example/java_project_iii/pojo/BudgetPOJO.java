package org.example.java_project_iii.pojo;

import java.util.Date;

public class BudgetPOJO extends DatabaseItemPojo{
    private int transaction_id;
    private double goal_amount;
    private Date start_date;
    private Date end_date;

    public BudgetPOJO(int budget_id, int transaction_id, double goal_amount, Date start_date, Date end_date) {
        super(budget_id);
        this.transaction_id = transaction_id;
        this.goal_amount = goal_amount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
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
