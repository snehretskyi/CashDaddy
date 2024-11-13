package pojo;

public class Transaction_categoryPOJO extends DatabaseItemPojo {
    private int category_id;

    public Transaction_categoryPOJO(int transaction_id, int category_id) {
        super(transaction_id);
        this.category_id = category_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
