package com.inventar.nuni.inventari;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nuni on 28/08/2017.
 */

public class DatabazeCon extends SQLiteOpenHelper {

    public static final String DB_NAME = "artikujt.db";
    public static final String TABLE_NAME = "artikull_informacion";
    public static final String ID = "ID";
    public static final String PERSHKRIM = "PERSHKRIM";
    public static final String NJESI = "NJESI";
    public static final String KATEGORI = "KATEGORI";
    public static final String CMIMI = "CMIMI";
    public static final String DATA = "DATA";
    //private SQLiteDatabase db;
    private Context context;


    public DatabazeCon(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(ID STRING unique ,PERSHKRIM TEXT ," +
                "NJESI TEXT,KATEGORI TEXT,CMIMI STRING,DATA STRING)");
        //sqLiteDatabase.execSQL("CREATE TABLE artikuj_shitje (ID TEXT , NJESIA TEXT, SHITJE TEXT, DATA TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void shto_artikull(String id,String pershkrim, String njesi, String kategori, String cmim, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
try {
    db.beginTransaction();
    contentValues.put(ID, id);
    contentValues.put(PERSHKRIM, pershkrim);
    contentValues.put(NJESI, njesi);
    contentValues.put(KATEGORI, kategori);
    contentValues.put(CMIMI, cmim);
    contentValues.put(DATA, date);
    //db.insert(TABLE_NAME, null, contentValues);
    db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
    db.setTransactionSuccessful();
}finally {
    db.endTransaction();
}
    }

    public Cursor shfaq_info() {
        SQLiteDatabase db = getWritableDatabase();

        //Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME, null);
        // Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME+ " ORDER BY "+ ID +" DESC", null);
        return db.rawQuery("SELECT * from " + TABLE_NAME + " ORDER BY "+ DATA +" DESC", null);
    }
    public int count_info() {
        SQLiteDatabase db = getWritableDatabase();

        int count=0;
        //Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME, null);
        // Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME+ " ORDER BY "+ ID +" DESC", null);
        Cursor rezultate = db.rawQuery("SELECT count(1) from " + TABLE_NAME + " ORDER BY "+ DATA +" DESC", null);
        if(rezultate.moveToFirst()){
            count=rezultate.getInt(0);
        }
        rezultate.close();
        return count;
    }

    public Cursor get_newer(int id) {
        SQLiteDatabase db = getWritableDatabase();

        //Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME, null);
        // Cursor rezultate = db.rawQuery("select * from " + TABLE_NAME+ " ORDER BY "+ ID +" DESC", null);
        return  db.rawQuery("SELECT * from " + TABLE_NAME + " WHERE ID>"+id+" ORDER BY "+ DATA +" DESC", null);

    }

    public void delete(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }


    public long getTaskCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    public Cursor getInfoProdukt(String id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID="+id,null);
        return cursor;

    }

}
