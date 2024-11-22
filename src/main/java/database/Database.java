package database;


import java.sql.*;

import static database.Const.*;

/**
 * This class is using the Singleton Design Pattern so that the entire application shares one connection
 * to the database;
 * We do this through the use of:
 *  - public static instance variable
 *  - private constructor
 *  - getInstance() method
 */
public class Database {

    //public static instance variable
    private static Database instance;
    private Connection connection;
    private Const credentialsConst = new Const();

    //private constructor
    private Database() throws Exception {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME +
                            "?serverTimeZone=UTC",
                            DB_USER,
                            DB_PASS);
            System.out.println("Created Connection!");
        }catch (Exception e){
            throw new Exception("Couldn't connect!");
        }

        createTable(DBConst.TABLE_TRANSACTION_TYPES, DBConst.CREATE_TABLE_TRANSACTION_TYPES, connection);
        createTable(DBConst.TABLE_ACCOUNTS, DBConst.CREATE_TABLE_ACCOUNTS, connection);
        createTable(DBConst.TABLE_CATEGORIES, DBConst.CREATE_TABLE_CATEGORIES, connection);
        createTable(DBConst.TABLE_BUDGETS, DBConst.CREATE_TABLE_BUDGETS, connection);
        createTable(DBConst.TABLE_TRANSACTIONS, DBConst.CREATE_TABLE_TRANSACTIONS, connection);
        createTable(DBConst.TABLE_RECURRING_TRANSACTION, DBConst.CREATE_TABLE_RECURRING_TRANSACTION, connection);

        insertDefaultValues(DBConst.TABLE_TRANSACTION_TYPES, DBConst.INSERT_TRANSACTION_TYPES, connection);
        insertDefaultValues(DBConst.TABLE_CATEGORIES, DBConst.INSERT_CATEGORIES, connection);
        insertDefaultValues(DBConst.TABLE_ACCOUNTS, DBConst.INSERT_ACCOUNTS, connection);

    }

    public static Database getInstance() throws Exception {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates a table if it does not already exist.
     * Checks if the table exists and executes the `CREATE TABLE` query if it doesn't.
     *
     * @param tableName The name of the table to create.
     * @param tableQuery The SQL query to create the table.
     * @param connection The database connection.
     * @throws SQLException If an error occurs during the check or creation.
     */

    public void createTable(String tableName, String tableQuery, Connection connection) throws SQLException {
        Statement createTable;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables(DB_NAME, null, tableName, null);
        if(resultSet.next()){
            System.out.println(tableName + " table already exists");
        }
        else{
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
    public void insertDefaultValues(String tableName, String insertQuery, Connection connection) throws SQLException {
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


}
