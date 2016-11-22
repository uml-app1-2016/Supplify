package com.example.supplify2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by George on 11/17/2016.
 */

public class HistoryDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = HistoryDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "history.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HistoryDbHelper}.
     *
     * @param context of the app
     */
    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + HistoryContract.HistoryItem.TABLE_NAME + " ("
                + HistoryContract.HistoryItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HistoryContract.HistoryItem.COLUMN_SUPP_NAME        + " TEXT NOT NULL, "
                + HistoryContract.HistoryItem.COLUMN_SUPP_DESCRIPTION + " TEXT, "
                + HistoryContract.HistoryItem.COLUMN_SUPP_DOSAGE      + " TEXT, "
                + HistoryContract.HistoryItem.COLUMN_SUPP_DOSAGE      + " TEXT";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}