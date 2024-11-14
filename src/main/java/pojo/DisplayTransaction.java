package pojo;

public class DisplayTransaction {


    //transaction_id, account_id, amount, transaction_type_id, transaction_date, description

    private int transaction_id;
    private String account_name;
    private String transaction_type;
    private String transaction_amount;
    private String transaction_date;
    private String transaction_description;

    public DisplayTransaction(int transaction_id, String account_name, String transaction_type, String transaction_amount, String transaction_date, String transaction_description) {
        this.transaction_id = transaction_id;
        this.account_name = account_name;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.transaction_date = transaction_date;
        this.transaction_description = transaction_description;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }
}
