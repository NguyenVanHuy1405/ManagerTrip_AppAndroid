package com.example.final_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {


    private dataSearch item;
    private Context context;
    private SQLiteDatabase database;
    private static final String DATABASE_NAME ="Trip.db";
    //private static final int DATABASE_VERSION= 1;

    private static final String TABLE_NAME = "project_mobile";
    private static final String C0LUMN_ID = "Id";
    private static final String C0LUMN_NAME = "name";
    private static final String C0LUMN_Destination = "destination";
    private static final String C0LUMN_DateOfTrip = "date";
    private static final String C0LUMN_RequireRisks = "risk";
    private static final String C0LUMN_Description = "description";

    private static final String TABLE_NAME1 = "Expense_trip";
    private static final String C0LUMN_ID1 = "Id_expense";
    private static final String C0LUMN_TripId = "Trip_id";
    private static final String C0LUMN_TYPE = "Type";
    private static final String C0LUMN_AMOUNT = "Amount";
    private static final String C0LUMN_TIME = "Time";

    Database(@Nullable Context context) {

        super(context, DATABASE_NAME,null,1);
        this.context = context;
        database = getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase Database) {
        String table_trip =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + C0LUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        C0LUMN_NAME + " TEXT, " +
                        C0LUMN_Destination + " TEXT, " +
                        C0LUMN_DateOfTrip + " TEXT, " +
                        C0LUMN_RequireRisks + " INTEGER, " +
                        C0LUMN_Description + " TEXT);";
        String table_expense =
                "CREATE TABLE " + TABLE_NAME1 +
                        " (" + C0LUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        C0LUMN_TYPE + " INTEGER, " +
                        C0LUMN_AMOUNT + " TEXT, " +
                        C0LUMN_TIME  + " TEXT, "+
                        C0LUMN_TripId + " INTEGER, " +
                        " FOREIGN KEY ("+C0LUMN_TripId+") REFERENCES "+TABLE_NAME+"("+C0LUMN_ID+"));";
        Database.execSQL(table_trip);
        Database.execSQL(table_expense);

    }

    @Override
    public void onUpgrade(SQLiteDatabase Database, int i, int i1) {
        Database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(Database);
    }
    void addTrip(String name, String destination,String date,int risks, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(C0LUMN_NAME,name);
        cv.put(C0LUMN_Destination,destination);
        cv.put(C0LUMN_DateOfTrip,date);
        cv.put(C0LUMN_RequireRisks,risks);
        cv.put(C0LUMN_Description,description);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_LONG).show();
        }

    }

    void addExpense(String trip_id, int type, String amount, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(C0LUMN_TripId,trip_id);
        cv.put(C0LUMN_TYPE,type);
        cv.put(C0LUMN_AMOUNT,amount);
        cv.put(C0LUMN_TIME,time);

        long result = db.insert(TABLE_NAME1,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_LONG).show();
        }
    }
    //Add expenses
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor expenseData(){
        String query = "SELECT * FROM " + TABLE_NAME1;
//        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE " +C0LUMN_TripId+ "='" + "1'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

//        Cursor cursor = database.query(TABLE_NAME1,
//                new String[]{
//                        C0LUMN_ID1,C0LUMN_TripId,C0LUMN_TYPE,C0LUMN_AMOUNT,C0LUMN_TIME
//                }, null, null, null, null,C0LUMN_ID1
//                );
        return cursor;
    }


    void updateDatabase(String row_id, String name, String destination,String date,String risks, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(C0LUMN_NAME,name);
        cv.put(C0LUMN_Destination,destination);
        cv.put(C0LUMN_DateOfTrip,date);
        cv.put(C0LUMN_RequireRisks,risks);
        cv.put(C0LUMN_Description,description);

        long result = db.update(TABLE_NAME, cv, "Id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to upload", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Successfully update", Toast.LENGTH_LONG).show();
        }
    }

    void deleteOnRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "Id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to delete", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Successfully delete trip", Toast.LENGTH_LONG).show();
        }
    }
    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }

    //Search by key word
    public List<dataSearch> searchByKey(String key){
        List<dataSearch> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query(TABLE_NAME, null, whereClause, whereArgs, null,null,null);
        while (rs != null && rs.moveToNext()){
            String id = rs.getString(0);
            String name= rs.getString(1);
            String destination= rs.getString(2);
            String risk= rs.getString(3);
            String description= rs.getString(4);
            String date = rs.getString(5);
            list.add(new dataSearch(id,name, destination,date, risk, description));
        }
        return list;
    }

    //Search by date
    public List<dataSearch> searchByDate(String from, String to){
        List<dataSearch> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArgs, null,null,null);
        while (rs != null && rs.moveToNext()){
            String id = rs.getString(0);
            String name= rs.getString(1);
            String destination= rs.getString(2);
            String risk= rs.getString(3);
            String description= rs.getString(4);
            String date = rs.getString(5);
            list.add(new dataSearch(id, name, destination, date, risk, description));
        }
        return list;
    }
    public List<dataSearch> getAll(){
        List<dataSearch> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order= "date ASC";
        Cursor rs = st.query(TABLE_NAME, null, null,
                null, null,null, order);
        while(rs != null && rs.moveToNext()){
            String id = rs.getString(0);
            String name= rs.getString(1);
            String destination= rs.getString(2);
            String date= rs.getString(3);
            String risk= rs.getString(4);
            String description= rs.getString(5);
            //add to
            list.add(new dataSearch(id, name, destination,date, risk));
        }
        return list;
    }
}
