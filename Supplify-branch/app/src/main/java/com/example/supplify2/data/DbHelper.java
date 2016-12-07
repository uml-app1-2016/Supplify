package com.example.supplify2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/17/2016.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DbHelper.class.getSimpleName();



    // Name of the database file
    private static final String DATABASE_NAME = "history.db";

    // Database version. If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Columns of our database
    public static final String SUPPS_TABLE_NAME = "supps";
    public static final String SUPPS_COLUMN_ID = "id";
    public static final String SUPPS_COLOUMN_NAME = "name";
    public static final String SUPPS_COLUMN_DESCRIPTION = "description";
    public static final String SUPPS_COLUMN_DOSAGE = "dosage";
    public static final String SUPPS_COLUMN_EFFECTS = "effects";

    /**
     * Constructs a new instance of {@link DbHelper}.
     *
     * @param context of the app
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_SUPPS_TABLE =  "CREATE TABLE " + SUPPS_TABLE_NAME + "(" +
              SUPPS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
              SUPPS_COLOUMN_NAME + "TEXT NOT NULL, " +
              SUPPS_COLUMN_DESCRIPTION + " TEXT, " +
              SUPPS_COLUMN_DOSAGE + " TEXT, " +
              SUPPS_COLUMN_EFFECTS + " TEXT " + ")";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SUPPS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS supps");
        onCreate(db);
    }

    public boolean checkDatabase() {
        SQLiteDatabase db = getReadableDatabase();
        return (db != null);
    }

    /** Function to insert a supplement into our database */
    public boolean insertSupp (Supp supp) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", supp.getName());
        contentValues.put("description", supp.getDescription());
        contentValues.put("dosage", supp.getDosage());
        contentValues.put("effect", supp.getEffect());
        db.insert(SUPPS_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    /** Function to insert multiple supplements into our database */
    public boolean insertSupps(List<Supp> supps) {
        SQLiteDatabase db = getWritableDatabase();

        int count = 0;
        for(Supp newSupp : supps) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", newSupp.getName());
            contentValues.put("description", newSupp.getDescription());
            contentValues.put("dosage", newSupp.getDosage());
            contentValues.put("effect", newSupp.getEffect());
            long result = db.insert("supps", null, contentValues);
            if(result == -1 ) {
                Log.i(getClass().getSimpleName(),("INFO: Error inserting supplement" + newSupp.getName()));
            }
            else {
                count++;
            }
        }

        Log.i(getClass().getSimpleName(), ("INFO: Supplements inserted =>" + count));

        return true;
    }

    /** Function to retrieve supplement data by ID */
    public Cursor getData(int _ID) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(("SELECT * FROM " + SUPPS_TABLE_NAME +
                            " WHERE " + SUPPS_COLUMN_ID +
                            " = " + _ID), null);
    }

    /** Function to see the number of items in our database */
    public int numberOfItems() {
        SQLiteDatabase db = getReadableDatabase();
        return( (int) DatabaseUtils.queryNumEntries(db, SUPPS_TABLE_NAME));
    }

    /** Function to delete a supplement in our database */
    public int deleteSupp(int _ID) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(SUPPS_TABLE_NAME, (SUPPS_COLUMN_ID + " = ? "),
                new String[] {
                        String.valueOf(_ID)
                });
    }

    /** Function to retrieve the supplement by it's name */
    public Supp getSuppByName(String name) {
        Supp supp = new Supp();

        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(("SELECT * FROM " + SUPPS_TABLE_NAME + " WHERE "
                + SUPPS_COLOUMN_NAME + "=\"" + name + "\""), null);

        res.moveToFirst();
        while(!res.isAfterLast()) {
            supp.set_ID(Integer.parseInt(res.getString(res.getColumnIndex(SUPPS_COLUMN_ID))));
            supp.setName(name);
            supp.setDescription((res.getString(res.getColumnIndex(SUPPS_COLUMN_DESCRIPTION))));
            supp.setDosage(res.getString(res.getColumnIndex(SUPPS_COLUMN_DOSAGE)));
            supp.setEffect(res.getString(res.getColumnIndex(SUPPS_COLUMN_EFFECTS)));
            res.moveToNext();
        }

        return supp;
    }


    /** Function to check if the supplement exists */
    public boolean suppExist(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(("SELECT * FROM " + SUPPS_TABLE_NAME + " WHERE "
                + SUPPS_COLOUMN_NAME + "=\"" + name + "\""), null);
        if(res.getCount() <= 0) {
            res.close();
            return false;
        }
        else {
            res.close();
            return true;
        }
    }
}