package com.inventar.nuni.inventari.info;

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

public class ArtikujDatabaze extends SQLiteOpenHelper {

    public static final String DB_NAME = "artikujt.db";
    public static final String TABLE_NAME = "artikuj_shitje";
    public static final String ID = "ID";
    public static final String NJESI = "NJESI";
    public static final String SASI = "SASI";
    public static final String DATA = "DATA";
    private SQLiteDatabase db;
    private Context context;

    public ArtikujDatabaze(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(ID STRING ,NJESI TEXT ," +
                "SASI TEXT,DATA STRING)");
        db=openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(eid VARCHAR,ename VARCHAR,esdate VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void shto_artikuj_shitje(String id,String njesi,String cmim,String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            db.beginTransaction();
            contentValues.put("ID", id);
            contentValues.put("NJESIA", njesi);
            contentValues.put("SHITJE", cmim);
            contentValues.put("DATA", date);
            db.insert("artikuj_shitje", null, contentValues);
            //db.insertWithOnConflict("artikuj_shitje", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public Cursor merr_artikuj_shitje(String id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID="+id,null);
        return cursor;
    }

}
