package database;

import java.sql.*;

import static database.Const.DB_NAME;

public class DBHelper {


    /**
     * Creates a table if it does not already exist.
     * Checks if the table exists and executes the `CREATE TABLE` query if it doesn't.
     *
     * @param tableName The name of the table to create.
     * @param tableQuery The SQL query to create the table.
     * @param connection The database connection.
     * @throws SQLException If an error occurs during the check or creation.
     */

    public static void createTable (String tableName, String tableQuery, Connection connection) throws SQLException {
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
     * Inserts default values into the table if they do not exist.
     * Checks if the table and values are present, and if not, executes the `INSERT INTO` query.
     *
     * @param tableName The table to insert values into.
     * @param insertQuery The SQL query for inserting default values.
     * @param connection The database connection.
     * @throws SQLException If an error occurs during the check or insertion.
     */
    public static void insertDefaultValues (String tableName, String insertQuery, Connection connection) throws
    SQLException {
        Statement statement;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);

        if (resultSet.next()) {
            // Check if the table exists, now check if the necessary values exist
            String checkQuery = "SELECT COUNT(*) FROM " + tableName;
            statement = connection.createStatement();
            ResultSet countResultSet = statement.executeQuery(checkQuery);

            // Assuming there's a specific way to check if data is missing (e.g., counting rows)
            if (countResultSet.next() && countResultSet.getInt(1) > 0) {
                System.out.println("Default values already inserted into the " + tableName + " table.");
            } else {
                // Execute the insert query if values are missing
                statement.execute(insertQuery);
                System.out.println("Default values have been inserted into the " + tableName + " table.");
            }
        } else {
            System.out.println(tableName + " table does not exist.");
        }
    }

    public static void insertDefaultValuesForTransactions(String tableName, String insertQuery, Connection connection) throws SQLException {
        Statement statement;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);

        if (resultSet.next()) {
            // Check if the table exists, now check if the necessary values exist
            String checkQuery = "SELECT COUNT(*) FROM " + tableName;
            statement = connection.createStatement();
            ResultSet countResultSet = statement.executeQuery(checkQuery);

            // Assuming there's a specific way to check if data is missing (e.g., counting rows)
            if (countResultSet.next() && countResultSet.getInt(1) > 0) {
                System.out.println("Default values already inserted into the " + tableName + " table.");
            } else {
                // Reset AUTO_INCREMENT to 1
                String resetAutoIncrementQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
                statement.execute(resetAutoIncrementQuery);
                System.out.println("AUTO_INCREMENT reset to 1 for table: " + tableName);
                // Execute the insert query if values are missing
                statement.execute(insertQuery);
                System.out.println("Default values have been inserted into the " + tableName + " table.");
            }
        } else {
            System.out.println(tableName + " table does not exist.");
        }
    }





}
