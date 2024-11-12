package pojo;

public class Transaction_typePOJO {
    private int transaction_type_id;
    private String transaction_type;

    public Transaction_typePOJO(int transaction_type_id, String transaction_type) {
        this.transaction_type_id = transaction_type_id;
        this.transaction_type = transaction_type;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String toString() {return transaction_type;}
}
