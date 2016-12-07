package com.example.supplify2.data;

import android.app.Application;

/**
 * Created by George on 12/6/2016.
 */

public class GlobalState extends Application {
    /** Global variables for our application */
    private DbHelper db;

    /** Accessor methods for our global variables */
    public DbHelper getDatabase() {
        return db;
    }

    /** Mutator methods for our global variables */
    public void setDatabase(DbHelper newDb) {
        db = newDb;
    }
}
