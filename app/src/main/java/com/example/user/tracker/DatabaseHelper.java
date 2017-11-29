package com.example.user.tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 29-11-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tracking.db";
    public static final String TABLE_NAME = "trackDetails";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SHOP";
    public static final String COL_3 = "IN_TIME";
    public static final String COL_4 = "IN_LOCATION";
    public static final String COL_5 = "OUT_TIME";
    public static final String COL_6 = "OUT_LOCATION";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2
        +" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT, "+COL_6+" TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String shopName,String inTime,String inLocation,String outTime,String outLocation){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,shopName);
        contentValues.put(COL_3,inTime);
        contentValues.put(COL_4,inLocation);
        contentValues.put(COL_5,outTime);
        contentValues.put(COL_6,outLocation);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
            else
                return true;
    }
    public Cursor getAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

//    public boolean updateData(String id,String shopName,String inTime,String inLocation,String outTime,String outLocation){
//
//
//        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(COL_2,shopName);
//        contentValues.put(COL_3,inTime);
//        contentValues.put(COL_4,inLocation);
//        contentValues.put(COL_5,outTime);
//        contentValues.put(COL_6,outLocation);
//
//        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }


    public boolean updateData(String id,String outTime, String outLocation) {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
//        contentValues.put(COL_2,shop);
//        contentValues.put(COL_3,inTime);
//        contentValues.put(COL_4,inLocation);
        contentValues.put(COL_5,outTime);
        contentValues.put(COL_6,outLocation);

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] {String.valueOf(id)});
        return true;
    }
}
