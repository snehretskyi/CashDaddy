package pojo;

public class DisplayTransaction {


    //transaction_id, account_id, amount, transaction_type_id, transaction_date, description

    private int id;
    private String account_name;
    private String type;
    private String amount;
    private String date;
    private String description;

    public DisplayTransaction(int id, String account_name, String type, String amount, String date, String description) {
        this.id = id;
        this.account_name = account_name;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
