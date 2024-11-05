package database;

public class DBConst {

        /**
         * ACCOUNTS TABLE
         */

        public static final String TABLE_ACCOUNTS= "accounts";
        public static final String ACCOUNTS_COLUMN_ID= "account_id";
        public static final String ACCOUNTS_COLUMN_ACCOUNT_TYPE = "account_type";
        public static final String ACCOUNTS_COLUMN_BALANCE = "account_balance";
        public static final String ACCOUNTS_COLUMN_INFORMATION = "account_information";

        /**
         * BUDGETS TABLE
         */

        public static final String TABLE_BUDGETS = "budgets";
        public static final String BUDGETS_COLUMN_ID = "budget_id";
        public static final String BUDGETS_COLUMN_CATEGORY_ID = "category_id";
        public static final String BUDGETS_COLUMN_GOAL_AMOUNT = "goal_amount";
        public static final String BUDGETS_COLUMN_START_DATE = "start_date";
        public static final String BUDGETS_COLUMN_END_DATE = "end_date";

        /**
         * TRANSACTIONS TABLE
         */
        public static final String TABLE_TRANSACTIONS = "transactions";
        public static final String TRANSACTIONS_COLUMN_ID = "transaction_id";
        public static final String TRANSACTIONS_COLUMN_ACCOUNT_ID = "account_id";
        public static final String TRANSACTIONS_COLUMN_AMOUNT = "amount";
        public static final String TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID = "transaction_type_id";
        public static final String TRANSACTIONS_COLUMN_TRANSACTION_DATE = "transaction_date";
        public static final String TRANSACTIONS_COLUMN_DESCRIPTION = "description";

        /**
         * TRANSACTION_TYPE TABLE
         */

        public static final String TABLE_TRANSACTION_TYPES = "transaction_types";
        public static final String TRANSACTION_TYPES_COLUMN_ID = "transaction_type_id";
        public static final String TRANSACTION_TYPES_COLUMN_TYPE = "type";

        /**
         * CATEGORIES TABLE
         */
        public static final String TABLE_CATEGORIES = "categories";
        public static final String CATEGORIES_COLUMN_ID = "category_id";
        public static final String CATEGORIES_COLUMN_BUDGET_ID = "budget_id";
        public static final String CATEGORIES_COLUMN_CATEGORY_TYPE = "category_type";

        /**
         * TRANSACTION_CATEGORY TABLE
         */
        public static final String TABLE_TRANSACTION_CATEGORY = "transaction_category";
        public static final String TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID = "transaction_id";
        public static final String TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID = "category_id";


        /**
         * RECURRING_TRANSACTION TABLE
         */
        public static final String TABLE_RECURRING_TRANSACTION = "recurring_transaction";
        public static final String RECURRING_TRANSACTION_COLUMN_ID = "recurring_transaction_id";
        public static final String RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID = "transaction_id";
        public static final String RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS = "interval_days";

        // CREATE TABLES

        // ACCOUNTS TABLE
        public static final String CREATE_TABLE_ACCOUNTS =
                "CREATE TABLE " + TABLE_ACCOUNTS + " (" +
                        ACCOUNTS_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        ACCOUNTS_COLUMN_ACCOUNT_TYPE + " VARCHAR(50), " +
                        ACCOUNTS_COLUMN_BALANCE + " DECIMAL(10, 2), " +
                        ACCOUNTS_COLUMN_INFORMATION + " VARCHAR(255), " +
                        "PRIMARY KEY (" + ACCOUNTS_COLUMN_ID + ") );";

        // BUDGETS TABLE
        public static final String CREATE_TABLE_BUDGETS =
                "CREATE TABLE " + TABLE_BUDGETS + " (" +
                        BUDGETS_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        BUDGETS_COLUMN_CATEGORY_ID + " INT, " +
                        BUDGETS_COLUMN_GOAL_AMOUNT + " DECIMAL(10, 2), " +
                        BUDGETS_COLUMN_START_DATE + " DATE, " +
                        BUDGETS_COLUMN_END_DATE + " DATE, " +
                        "PRIMARY KEY (" + BUDGETS_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + BUDGETS_COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + CATEGORIES_COLUMN_ID + ") );";

        // TRANSACTIONS TABLE
        public static final String CREATE_TABLE_TRANSACTIONS =
                "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                        TRANSACTIONS_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        TRANSACTIONS_COLUMN_ACCOUNT_ID + " INT, " +
                        TRANSACTIONS_COLUMN_AMOUNT + " DECIMAL(10, 2), " +
                        TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + " INT, " +
                        TRANSACTIONS_COLUMN_TRANSACTION_DATE + " DATE, " +
                        TRANSACTIONS_COLUMN_DESCRIPTION + " VARCHAR(255), " +
                        "PRIMARY KEY (" + TRANSACTIONS_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + TRANSACTIONS_COLUMN_ACCOUNT_ID + ") REFERENCES " + TABLE_ACCOUNTS + "(" + ACCOUNTS_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + ") REFERENCES " + TABLE_TRANSACTION_TYPES + "(" + TRANSACTION_TYPES_COLUMN_ID + ") );";

        // TRANSACTION_TYPE TABLE
        public static final String CREATE_TABLE_TRANSACTION_TYPES =
                "CREATE TABLE " + TABLE_TRANSACTION_TYPES + " (" +
                        TRANSACTION_TYPES_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        TRANSACTION_TYPES_COLUMN_TYPE + " VARCHAR(50), " +
                        "PRIMARY KEY (" + TRANSACTION_TYPES_COLUMN_ID + ") );";

        // CATEGORIES TABLE
        public static final String CREATE_TABLE_CATEGORIES =
                "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                        CATEGORIES_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        CATEGORIES_COLUMN_BUDGET_ID + " INT, " +
                        CATEGORIES_COLUMN_CATEGORY_TYPE + " VARCHAR(50), " +
                        "PRIMARY KEY (" + CATEGORIES_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + CATEGORIES_COLUMN_BUDGET_ID + ") REFERENCES " + TABLE_BUDGETS + "(" + BUDGETS_COLUMN_ID + ") );";

        // TRANSACTION_CATEGORY TABLE
        public static final String CREATE_TABLE_TRANSACTION_CATEGORY =
                "CREATE TABLE " + TABLE_TRANSACTION_CATEGORY + " (" +
                        TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + " INT, " +
                        TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + " INT, " +
                        "PRIMARY KEY (" + TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + ", " + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + "), " +
                        "FOREIGN KEY (" + TRANSACTION_CATEGORY_COLUMN_TRANSACTION_ID + ") REFERENCES " + TABLE_TRANSACTIONS + "(" + TRANSACTIONS_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + TRANSACTION_CATEGORY_COLUMN_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + CATEGORIES_COLUMN_ID + ") );";

        // RECURRING_TRANSACTION TABLE
        public static final String CREATE_TABLE_RECURRING_TRANSACTION =
                "CREATE TABLE " + TABLE_RECURRING_TRANSACTION + " (" +
                        RECURRING_TRANSACTION_COLUMN_ID + " INT NOT NULL AUTO_INCREMENT, " +
                        RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + " INT, " +
                        RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS + " INT, " +
                        "PRIMARY KEY (" + RECURRING_TRANSACTION_COLUMN_ID + "), " +
                        "FOREIGN KEY (" + RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + ") REFERENCES " + TABLE_TRANSACTIONS + "(" + TRANSACTIONS_COLUMN_ID + ") );";


}
