package pojo;

public class Transaction_typePOJO extends DatabaseItemPojo {
    private String transaction_type;

    public Transaction_typePOJO(int transaction_type_id, String transaction_type) {
        super(transaction_type_id);
        this.transaction_type = transaction_type;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String toString() {return transaction_type;}
}
