package pojo;

/**
 * Represents a displayable transaction with details
 */

public class DisplayTransaction {

    //class member variables
    private int id;
    private String account_name;
    private String type;
    private int amount;
    private String date;
    private String category;
    private String description;
    private String recurringStatus;
    private String intervalDays;

    /**
     * Constructs a DisplayTransaction with specified values.
     * @param id the unique identifier of the transaction
     * @param account_name the type of the account associated with the transaction
     * @param type the type of the transaction
     * @param amount the amount for the transaction
     * @param date the date the transaction occurred
     * @param description additional details about the transaction
     */

    public DisplayTransaction(int id, String account_name, String type, String category, int amount, String date, String description, String recurringStatus, String intervalDays ) {
        this.id = id;
        this.account_name = account_name;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.recurringStatus= recurringStatus;
        this.intervalDays = intervalDays;
    }

    public String getRecurringStatus() {
        return recurringStatus;
    }

    public void setRecurringStatus(String recurringStatus) {
        this.recurringStatus = recurringStatus;
    }

    public String getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(String intervalDays) {
        this.intervalDays = intervalDays;
    }

    /**
     * Gets the category associated with transaction
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the category associated with transaction
     * @param category set to the transaction
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the transaction ID
     * @return the transaction ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the transaction ID
     * @param id the transaction ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the account name associated with the transaction
     * @return the account name
     */
    public String getAccount_name() {
        return account_name;
    }

    /**
     * Sets the account name associated with the transaction
     * @param account_name the account name to set
     */
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    /**
     * Gets the transaction type
     * @return the transaction type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the transaction type
     * @param type the transaction type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the transaction amount
     * @return the transaction amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the transaction amount
     * @param amount the transaction amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the transaction date
     * @return the transaction date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the transaction date
     * @param date the transaction date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the transaction description
     * @return the transaction description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the transaction description
     * @param description the transaction description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}