package pojo;

public class AccountPOJO {
    private int account_id;
    private String type;
    private double balance;
    private String information;

    public AccountPOJO(int id, String type, double balance, String information) {
        this.account_id = id;
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

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
