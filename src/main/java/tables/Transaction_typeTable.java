package tables;

import dao.Transaction_typeDAO;
import database.Database;
import pojo.Transaction_typePOJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static database.DBConst.*;

public class Transaction_typeTable implements Transaction_typeDAO {

    /**
     * Singleton class for managing database operations on the Transaction_typeTable .
     */
    private static Transaction_typeTable instance;
    Database db=Database.getInstance();

    private Transaction_typeTable() throws Exception {
        db = Database.getInstance();
    }

    public static Transaction_typeTable getInstance() throws Exception {
        if(instance == null){
            instance = new Transaction_typeTable();
        }
        return instance;
    }


    ArrayList<Transaction_typePOJO> transaction_type;

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
    public ArrayList<Transaction_typePOJO> getAllTransaction_types() {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES;
        transaction_type = new ArrayList<>();
        try {
            Statement getType = getDb().getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                transaction_type.add(new Transaction_typePOJO(
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
    public Transaction_typePOJO getTransaction_type(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES +
                " WHERE " + TRANSACTION_TYPES_COLUMN_ID + " = " + id;
        try{
            Statement getType = getDb().getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            if(data.next()){
                Transaction_typePOJO category = new Transaction_typePOJO(
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
