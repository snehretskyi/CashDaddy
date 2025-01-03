package org.example.java_project_iii.database;

import java.sql.*;

import static org.example.java_project_iii.database.Const.DB_NAME;
import static org.example.java_project_iii.database.DBConst.*;

/**
 * Provides helper methods for database operations
 */

public class DBHelper {

    /**
     * Create a table if it doesn't exist
     *
     * @param tableName  The name of the table to create
     * @param tableQuery The SQL query to create the table
     * @param connection database connection
     * @throws SQLException If an error occurs
     */

    public static void createTable(String tableName, String tableQuery, Connection connection) throws SQLException {
        Statement createTable;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);
        if (resultSet.next()) {
            System.out.println(tableName + " table already exists");
        } else {
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println("The " + tableName + " table has been created");
        }
    }

    /**
     * Insert default values into a table if they don't exist
     *
     * @param tableName   The table to insert values into
     * @param insertQuery SQL query for inserting default values
     * @param connection  database connection
     * @throws SQLException If an error occurs
     */
    public static void insertDefaultValues(String tableName, String insertQuery, Connection connection) throws
            SQLException {
        Statement statement;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);

        if (resultSet.next()) {

            String checkQuery = "SELECT COUNT(*) FROM " + tableName;
            statement = connection.createStatement();
            ResultSet countResultSet = statement.executeQuery(checkQuery);

            if (countResultSet.next() && countResultSet.getInt(1) > 0) {
                System.out.println("Default values already inserted into the " + tableName + " table.");
            } else {
                // Reset AUTO_INCREMENT and insert default values
                String resetAutoIncrementQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
                statement.execute(resetAutoIncrementQuery);
                System.out.println("AUTO_INCREMENT reset to 1 for table: " + tableName);

                // Insert default values
                statement.execute(insertQuery);
                System.out.println("Default values have been inserted into the " + tableName + " table.");
            }
        } else {
            System.out.println(tableName + " table does not exist.");
        }
    }

    /**
     * Inserts default values and updates balances.
     *
     * @param tableName   name of the table
     * @param insertQuery SQL insert query
     * @param connection  Database connection
     * @throws SQLException On SQL error
     */

    public static void insertDefaultValuesForTransactions(String tableName, String insertQuery, Connection connection) throws SQLException {
        Statement statement;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);

        if (resultSet.next()) {
            // Check if the table exists and has data
            String checkQuery = "SELECT COUNT(*) FROM " + tableName;
            statement = connection.createStatement();
            ResultSet countResultSet = statement.executeQuery(checkQuery);

            if (countResultSet.next() && countResultSet.getInt(1) > 0) {
                System.out.println("Default values already inserted into the " + tableName + " table.");
            } else {

                // Reset AUTO_INCREMENT and insert default values
                String resetAutoIncrementQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
                statement.execute(resetAutoIncrementQuery);
                System.out.println("AUTO_INCREMENT reset to 1 for table: " + tableName);

                // Execute the insert query if no values
                statement.execute(insertQuery);
                System.out.println("Default values have been inserted into the " + tableName + " table.");

                // Now, update account balances
                String updateBalanceQuery =
                        "UPDATE " + TABLE_ACCOUNTS + " a " +
                                "JOIN ( " +
                                "    SELECT " + TRANSACTIONS_COLUMN_ACCOUNT_ID + ", SUM(" + TRANSACTIONS_COLUMN_AMOUNT + ") AS total_amount " +
                                "    FROM " + TABLE_TRANSACTIONS + " " +
                                "    GROUP BY " + TRANSACTIONS_COLUMN_ACCOUNT_ID +
                                ") t " +
                                "ON a." + ACCOUNTS_COLUMN_ID + " = t." + TRANSACTIONS_COLUMN_ACCOUNT_ID + " " +
                                "SET a." + ACCOUNTS_COLUMN_BALANCE + " = t.total_amount";

                statement.executeUpdate(updateBalanceQuery);
                System.out.println("Account balances updated based on transactions.");
            }
        } else {
            System.out.println(tableName + " table does not exist.");
        }
    }

}
