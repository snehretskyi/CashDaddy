package pojo;

import java.sql.Date;

public class TransactionsPOJO extends DatabaseItemPojo {
    private int transaction_account_id;
    private double amount;
    private int transaction_type_id;
    private Date transaction_date;
    private String transaction_description;

    public TransactionsPOJO(int transaction_id, int account_id, double amount, int transaction_type_id, Date transaction_date, String transaction_description) {
        super(transaction_id);
        this.transaction_account_id = account_id;
        this.amount = amount;
        this.transaction_type_id = transaction_type_id;
        this.transaction_date = transaction_date;
        this.transaction_description = transaction_description;
    }

    public TransactionsPOJO(double amount, Date transaction_date, String transaction_description) {
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.transaction_description = transaction_description;
    }

    public TransactionsPOJO() {
    }

    public int getTransaction_account_id() {
        return transaction_account_id;
    }

    public void setTransaction_account_id(int transaction_account_id) {
        this.transaction_account_id = transaction_account_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }

    public String toString() {return transaction_description;}
}
