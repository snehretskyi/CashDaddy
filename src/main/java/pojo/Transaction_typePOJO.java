package pojo;

public class Transaction_typePOJO {
    private int transaction_type_id;
    private int transaction_type;

    public Transaction_typePOJO(int transaction_type_id, int transaction_type) {
        this.transaction_type_id = transaction_type_id;
        this.transaction_type = transaction_type;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public int getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(int transaction_type) {
        this.transaction_type = transaction_type;
    }
}
