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



}
