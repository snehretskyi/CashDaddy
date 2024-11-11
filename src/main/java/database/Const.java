package database;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Const {
    private static String dbCredentialsPath = "src/main/resources/db_credentials.txt";
    public static String DB_HOST;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASS;

    /**
     * Vars for ACCOUNTS table
     */
    public static final String TABLE_ACCOUNTS = "account";
    public static final String ACCOUNT_COLUMN_ID = "account_id";
    public static final String ACCOUNT_COLUMN_TYPE = "account_type";
    public static final String ACCOUNT_COLUMN_balance = "account_balance";
    public static final String ACCOUNT_COLUMN_INFORMATION = "account_information";
    /**
     * Vars for TRANSACTIONS table
     */
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String TRANS_COLUMN_ID  = "transaction_id";
    public static final String TRANS_COLUMN_ACCOUNT_ID = ACCOUNT_COLUMN_ID;
    public static final String TRANS_COLUMN_AMOUNT = "amount";
    public static final String TRANS_COLUMN_TYPE = "transaction_type";
    public static final String TRANS_COLUMN_DATE = "transaction_date";
    public static final String TRANS_COLUMN_DESCRIPTION = "transaction_description";

    /**
     * Vars for RECURRING_TRANSACTIONS table
     */
    public static final String TABLE_RECURRING_TRANSACTIONS = "recurring_transactions";
    public static final String REC_TRANS_ID = "recurring_transaction_id";
    public static final String REC_TRANS_TRANS_ID = TRANS_COLUMN_ID;
    public static final String REC_TRANS_INTERVAL = "interval";

    /**
     * Vars for CATEGORIES table
     */
    public static final String TABLE_CATEGORIES = "categories";
    public static final String CATEGORIES_COLUMN_ID = "category_id";
    //CATEGORIES_COLUMN_BUDGET_ID was here
    public static final String CATEGORIES_COLUMN_NAME = "category_name";
    /**
     * Vars for BUDGETS table
     * Category Id - created in categories
     * Budget id - created in budgets
     */
    public static final String TABLE_BUDGETS = "budgets";
    public static final String BUDGETS_COLUMN_ID = "budget_id";
    public static final String BUDGETS_COLUMN_CATEGORY_ID = CATEGORIES_COLUMN_ID;
    public static final String BUDGETS_COLUMN_GOAL_AMOUNT = "goal_amount";
    public static final String BUDGETS_COLUMN_START_DATE = "start_date";
    public static final String BUDGETS_COLUMN_END_DATE = "end_date";

    /**
     * This needs to be here if we're to get budget id from budgets
     */
    public static final String CATEGORIES_COLUMN_BUDGET_ID = BUDGETS_COLUMN_ID;



    /**
     * Vars for TRANSACTION_CATEGORY table
     */
    public static final String TABLE_TRANSACTION_CATEGORY = "transaction_category";
    public static final String TRANS_CATEGORY_ID = TRANS_COLUMN_ID;
    public static final String TRANS_CATEGORY_CATEGORY_ID = CATEGORIES_COLUMN_ID;



    public Const() {
        try (Scanner scanner = new Scanner(new File(dbCredentialsPath))){
            DB_HOST = scanner.nextLine();
            DB_NAME = scanner.nextLine();
            DB_USER = scanner.nextLine();
            DB_PASS = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
