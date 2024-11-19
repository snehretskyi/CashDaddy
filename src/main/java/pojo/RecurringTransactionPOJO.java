package pojo;

public class RecurringTransactionPOJO extends DatabaseItemPojo {
    private int transactionId;
    private int intervalDays;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public RecurringTransactionPOJO(int id, int transactionId, int intervalDays) {
        super(id);
        this.transactionId = transactionId;
        this.intervalDays = intervalDays;
    }
}
