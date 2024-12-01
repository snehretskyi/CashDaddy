package org.example.java_project_iii.database;


import org.example.java_project_iii.pojo.BudgetPOJO;
import org.example.java_project_iii.tables.BudgetTable;

import java.sql.*;

import static org.example.java_project_iii.database.Const.*;
import static org.example.java_project_iii.database.DBHelper.*;

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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME +
                                    "?serverTimeZone=UTC",
                            DB_USER,
                            DB_PASS);
            System.out.println("Created Connection!");
        } catch (Exception e) {
            throw new Exception("Couldn't connect!");
        }

        createTable(DBConst.TABLE_TRANSACTION_TYPES, DBConst.CREATE_TABLE_TRANSACTION_TYPES, connection);
        createTable(DBConst.TABLE_ACCOUNTS, DBConst.CREATE_TABLE_ACCOUNTS, connection);
        createTable(DBConst.TABLE_CATEGORIES, DBConst.CREATE_TABLE_CATEGORIES, connection);
        createTable(DBConst.TABLE_TRANSACTIONS, DBConst.CREATE_TABLE_TRANSACTIONS, connection);
        createTable(DBConst.TABLE_BUDGETS, DBConst.CREATE_TABLE_BUDGETS, connection);
        createTable(DBConst.TABLE_RECURRING_TRANSACTION, DBConst.CREATE_TABLE_RECURRING_TRANSACTION, connection);

        insertDefaultValues(DBConst.TABLE_TRANSACTION_TYPES, InsertValueQueries.INSERT_TRANSACTION_TYPES, connection);
        insertDefaultValues(DBConst.TABLE_CATEGORIES, InsertValueQueries.INSERT_CATEGORIES, connection);
        insertDefaultValues(DBConst.TABLE_ACCOUNTS, InsertValueQueries.INSERT_ACCOUNTS, connection);
        insertDefaultValuesForTransactions(DBConst.TABLE_TRANSACTIONS, InsertValueQueries.INSERT_TRANSACTIONS, connection);
        insertDefaultValuesForTransactions(DBConst.TABLE_RECURRING_TRANSACTION, InsertValueQueries.INSERT_RECURRING_TRANSACTIONS, connection);
        insertDefaultValues(DBConst.TABLE_BUDGETS, InsertValueQueries.INSERT_BUDGETS, connection);

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



}
