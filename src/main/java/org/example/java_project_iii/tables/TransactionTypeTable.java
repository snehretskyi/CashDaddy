package org.example.java_project_iii.tables;

import org.example.java_project_iii.dao.Transaction_typeDAO;
import org.example.java_project_iii.database.Database;
import org.example.java_project_iii.pojo.TransactionTypePOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.java_project_iii.database.DBConst.*;

public class TransactionTypeTable implements Transaction_typeDAO {

    /**
     * Singleton class for managing org.example.java_project_iii.database operations on the TransactionTypeTable .
     */
    private static TransactionTypeTable instance;
    Database db=Database.getInstance();

    private TransactionTypeTable() throws Exception {
        db = Database.getInstance();
    }

    public static TransactionTypeTable getInstance() throws Exception {
        if(instance == null){
            instance = new TransactionTypeTable();
        }
        return instance;
    }


    ArrayList<TransactionTypePOJO> transaction_type;

    public Database getDb() throws Exception {
        try {
            if(db == null){
                db = Database.getInstance();
            }
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<TransactionTypePOJO> getAllTransaction_types() {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES;
        transaction_type = new ArrayList<>();
        try {
            Statement getType = getDb().getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                transaction_type.add(new TransactionTypePOJO(
                        data.getInt(TRANSACTION_TYPES_COLUMN_ID),
                        data.getString(TRANSACTION_TYPES_COLUMN_TYPE)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaction_type;
    }

    @Override
    public TransactionTypePOJO getTransaction_type(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES +
                " WHERE " + TRANSACTION_TYPES_COLUMN_ID + " = " + id;
        try{
            Statement getType = getDb().getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            if(data.next()){
                TransactionTypePOJO category = new TransactionTypePOJO(
                        data.getInt(TRANSACTION_TYPES_COLUMN_ID),
                        data.getString(TRANSACTION_TYPES_COLUMN_TYPE)
                );
                return null;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
