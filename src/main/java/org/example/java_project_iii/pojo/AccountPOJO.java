package org.example.java_project_iii.pojo;

/**
 * Represents an account with type, balance, and information
 */
public class AccountPOJO extends DatabaseItemPojo {
    private String type;
    private double balance;
    private String information;

    /**
     * AccountPOJO constructor
     *
     * @param id          account id
     * @param type        account type
     * @param balance     account balance
     * @param information account info.
     */
    public AccountPOJO(int id, String type, double balance, String information) {
        super(id);
        this.type = type;
        this.balance = balance;
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
