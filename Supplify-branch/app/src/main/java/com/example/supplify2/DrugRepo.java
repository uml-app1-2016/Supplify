package com.example.supplify2;

/**
 * Created by yildiz on 12/7/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class DrugRepo {
    private DBHelper dbHelper;

    public DrugRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Drug drug) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Drug.KEY_name, Drug.name);

        // Inserting Row
        long Drug_Id = db.insert(Drug.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) Drug_Id;
    }

    public void delete(int Drug_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Drug.TABLE, Drug.KEY_ID + "= ?", new String[] { String.valueOf(Drug_Id) });
        db.close(); // Closing database connection
    }

    public void update(Drug Drug) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Drug.KEY_name, Drug.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Drug.TABLE, values, Drug.KEY_ID + "= ?", new String[] { String.valueOf(Drug.Drug_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<Drug>  getDrugList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Drug.KEY_ID + "," +
                Drug.KEY_name +
                " FROM " + Drug.TABLE;

        //Drug Drug = new Drug();
        ArrayList<Drug> DrugList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Drug drug = new Drug();
                drug.name = cursor.getString(cursor.getColumnIndex(drug.KEY_name));
                drug.Drug_ID = cursor.getInt(cursor.getColumnIndex(drug.KEY_ID));
                DrugList.add(drug);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return DrugList;

    }

    public ArrayList<String> getDrugNames() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Drug.KEY_ID + "," +
                Drug.KEY_name +
                " FROM " + Drug.TABLE;

        //Drug Drug = new Drug();
        ArrayList<String> DrugList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                DrugList.add(cursor.getString(cursor.getColumnIndex(Drug.KEY_name)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return DrugList;
    }

    public Drug getDrugById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Drug.KEY_ID + "," +
                Drug.KEY_name +
                " FROM " + Drug.TABLE
                + " WHERE " +
                Drug.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Drug Drug = new Drug();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                Drug.Drug_ID =cursor.getInt(cursor.getColumnIndex(Drug.KEY_ID));
                Drug.name =cursor.getString(cursor.getColumnIndex(Drug.KEY_name));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Drug;
    }

    public Drug getDrugByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Drug.KEY_ID + "," +
                Drug.KEY_name +
                " FROM " + Drug.TABLE
                + " WHERE " +
                Drug.KEY_name + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Drug Drug = new Drug();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(name) } );

        if (cursor.moveToFirst()) {
            do {
                Drug.Drug_ID =cursor.getInt(cursor.getColumnIndex(Drug.KEY_ID));
                Drug.name =cursor.getString(cursor.getColumnIndex(Drug.KEY_name));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Drug;
    }

}