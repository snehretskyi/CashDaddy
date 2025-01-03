package org.example.java_project_iii.database;

import static org.example.java_project_iii.database.DBConst.*;

/**
 * Purpose of this class is to write SQL queries to insert mock data into tables
 */

public class InsertValueQueries {

    /**
     * Insert transaction types.
     */
    public static final String INSERT_TRANSACTION_TYPES =
            "INSERT INTO " + TABLE_TRANSACTION_TYPES + " (" + TRANSACTION_TYPES_COLUMN_TYPE + ") " +
                    "VALUES ('Income'), ('Expense');";

    /**
     * Insert categories.
     */
    public static final String INSERT_CATEGORIES =
            "INSERT INTO " + TABLE_CATEGORIES + " (" + CATEGORIES_COLUMN_CATEGORY_TYPE + ") " +
                    "VALUES ('Dividend'), ('Rent'), ('Utilities'), ('Groceries'), ('Entertainment'), ('Shopping'), ('Travel'), ('Other');";

    /**
     * Insert accounts.
     */
    public static final String INSERT_ACCOUNTS =
            "INSERT INTO " + TABLE_ACCOUNTS + " (" + ACCOUNTS_COLUMN_ACCOUNT_TYPE + ", " +
                    ACCOUNTS_COLUMN_BALANCE + ", " + ACCOUNTS_COLUMN_INFORMATION + ") " +
                    "VALUES ('Savings Account', 1000.50, 'Personal savings account'), " +
                    "('Checking Account', 500.75, 'Primary checking account'), " +
                    "('Credit Account', 2000.00, 'Credit card account');";

    /**
     * Insert transactions.
     */
    public static final String INSERT_TRANSACTIONS =
            "INSERT INTO " + TABLE_TRANSACTIONS + " (" +
                    TRANSACTIONS_COLUMN_ACCOUNT_ID + ", " +
                    TRANSACTIONS_COLUMN_AMOUNT + ", " +
                    TRANSACTIONS_COLUMN_TRANSACTION_TYPE_ID + ", " +
                    TRANSACTIONS_COLUMN_CATEGORY_ID + ", " +
                    TRANSACTIONS_COLUMN_TRANSACTION_DATE + ", " +
                    TRANSACTIONS_COLUMN_DESCRIPTION + ") " +
                    "VALUES " +
                    "(1, 500.00, 1, 1, '2024-11-01', 'Salary'), " +
                    "(2, 100.00, 2, 2, '2024-11-02', 'Rent payment'), " +
                    "(3, 150.00, 2, 3, '2024-11-03', 'Groceries'), " +
                    "(1, 50.00, 2, 4, '2024-11-04', 'Utility bill'), " +
                    "(2, 200.00, 1, 5, '2024-11-05', 'Freelance work'), " +
                    "(3, 80.00, 2, 6, '2024-11-06', 'Dining out'), " +
                    "(1, 120.00, 2, 7, '2024-11-07', 'Entertainment'), " +
                    "(2, 250.00, 1, 8, '2024-11-08', 'Investment returns'), " +
                    "(3, 90.00, 2, 2, '2024-11-09', 'Shopping'), " +
                    "(1, 200.00, 1, 1, '2024-11-10', 'Bonus'), " +
                    "(2, 300.00, 2, 4, '2024-11-11', 'Credit card payment'), " +
                    "(3, 110.00, 2, 6, '2024-11-12', 'Transport'), " +
                    "(1, 350.00, 1, 8, '2024-11-13', 'Project earnings'), " +
                    "(2, 90.00, 2, 5, '2024-11-14', 'Insurance'), " +
                    "(3, 130.00, 2, 5, '2024-11-15', 'Grocery shopping'), " +
                    "(1, 300.00, 1, 6, '2024-11-16', 'Stock dividends'), " +
                    "(2, 200.00, 2, 7, '2024-11-17', 'Internet bill'), " +
                    "(3, 50.00, 2, 1, '2024-11-18', 'Coffee shop'), " +
                    "(1, 400.00, 1, 8, '2024-11-19', 'Side hustle income'), " +
                    "(2, 120.00, 2, 2, '2024-11-20', 'Gym membership');";

    /**
     * Insert recurring transactions.
     */
    public static final String INSERT_RECURRING_TRANSACTIONS =
            "INSERT INTO " + TABLE_RECURRING_TRANSACTION + " (" +
                    RECURRING_TRANSACTION_COLUMN_TRANSACTION_ID + ", " +
                    RECURRING_TRANSACTION_COLUMN_INTERVAL_DAYS + ", " +
                    RECURRING_TRANSACTION_NEXT_DATE + ") " +
                    "VALUES " +
                    "(1, 30, '2024-12-01'), " +
                    "(2, 30, '2024-12-02'), " +
                    "(5, 30, '2024-12-05'), " +
                    "(9, 30, '2024-12-07'), " +
                    "(16, 30, '2024-12-08');";

    /**
     * Insert values in Budget table
     */
    public static final String INSERT_BUDGETS =
            "INSERT INTO " + TABLE_BUDGETS + " (" +
                    BUDGETS_COLUMN_TRANSACTION_TYPE_ID + ", " +
                    BUDGETS_COLUMN_GOAL_AMOUNT + ", " +
                    BUDGETS_COLUMN_START_DATE + ", " +
                    BUDGETS_COLUMN_END_DATE + ") " +
                    "VALUES " +
                    "(2, 3000.00, '2024-11-01', '2024-12-30'), " +
                    "(1, 2500.00, '2024-11-01', '2024-12-30');";

}

