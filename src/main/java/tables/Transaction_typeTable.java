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
    Database db = Database.getInstance();
    ArrayList<Transaction_typePOJO> transaction_type;

    public Transaction_typeTable() throws Exception {
    }

    @Override
    public ArrayList<Transaction_typePOJO> getAllTransaction_types() {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES;
        transaction_type = new ArrayList<>();
        try {
            Statement getType = db.getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            //data.next() makes data the first record, then the next record etc.
            while (data.next()) {
                transaction_type.add(new Transaction_typePOJO(
                        data.getInt(TRANSACTION_TYPES_COLUMN_ID),
                        data.getInt(TRANSACTION_TYPES_COLUMN_TYPE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction_type;
    }

    @Override
    public Transaction_typePOJO getTransaction_type(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES +
                " WHERE " + TRANSACTION_TYPES_COLUMN_ID + " = " + id;
        try{
            Statement getType = db.getConnection().createStatement();
            ResultSet data = getType.executeQuery(query);
            if(data.next()){
                Transaction_typePOJO category = new Transaction_typePOJO(
                        data.getInt(TRANSACTION_TYPES_COLUMN_ID),
                        data.getInt(TRANSACTION_TYPES_COLUMN_TYPE)
                );
                return null;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
