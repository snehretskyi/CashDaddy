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

}
