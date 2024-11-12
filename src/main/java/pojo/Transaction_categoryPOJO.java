package pojo;

public class Transaction_categoryPOJO {
    private int transaction_id;
    private int category_id;

    public Transaction_categoryPOJO(int transaction_id, int category_id) {
        this.transaction_id = transaction_id;
        this.category_id = category_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
