package org.example.java_project_iii.pojo;

public class TransactionTypePOJO extends DatabaseItemPojo {
    private String transactionType;

    public TransactionTypePOJO(int transaction_type_id, String transactionType) {
        super(transaction_type_id);
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String toString() {return transactionType;}
}
