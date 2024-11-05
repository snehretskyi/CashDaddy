package database;


import java.sql.Connection;
import java.sql.DriverManager;

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
    //private constructor
    private Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost/" + DB_NAME +
                            "?serverTimeZone=UTC",
                            DB_USER,
                            DB_PASS);
            System.out.println("Created Connection!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

}
