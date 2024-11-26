package pojo;

import tables.TransactionsTable;

import java.sql.Date;
import java.time.LocalDate;

public class RecurringTransactionPOJO extends DatabaseItemPojo {
    private int transactionId;
    private int intervalDays;
    private Date nextDate;

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

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

    /**
     * @param id
     * @param transactionId
     * @param intervalDays
     * @param nextDate
     */
    public RecurringTransactionPOJO(int id, int transactionId, int intervalDays, Date nextDate) {
        super(id);
        this.transactionId = transactionId;
        this.intervalDays = intervalDays;
        this.nextDate = nextDate;
    }

    /**
     * If nextDate is not provided, it will be automatically calculated
     * @param id
     * @param transactionId
     * @param intervalDays
     */
    public RecurringTransactionPOJO(int id, int transactionId, int intervalDays) throws Exception {
        super(id);
        this.transactionId = transactionId;
        this.intervalDays = intervalDays;
        // this is not optimal as I convert Date to LocalDate and then again back to Date
        LocalDate transactionDate =  TransactionsTable.getInstance().getTransaction(transactionId).getTransaction_date().toLocalDate();
        this.nextDate = Date.valueOf(transactionDate.plusDays(intervalDays));
    }

    /**
     * Method that sets the next date by adding the interval in days to the transaction date
     */
    public void calculateNextDate() throws Exception {
        LocalDate transactionDate =  TransactionsTable.getInstance().getTransaction(transactionId).getTransaction_date().toLocalDate();
        this.nextDate = Date.valueOf(transactionDate.plusDays(intervalDays));
    }

    /**
     * Method that sets the next date by adding the interval in days to the transaction date
     * @param transactionDate LocalDate transaction date
     */
    public void calculateNextDate(LocalDate transactionDate) {
        this.nextDate = Date.valueOf(transactionDate.plusDays(intervalDays));
    }
}
