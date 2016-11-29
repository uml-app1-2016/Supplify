package com.example.supplify2.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by George on 11/17/2016.
 */

public class HistoryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // ... give this an empty constructor
    private HistoryContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.history";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.supplify/history/ is a valid path for
     * looking at past searches. content://com.example.android.supplify/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_HISTORY = "HISTORY";

    /**
     * Inner class that defines constant values for the past searches database table.
     * Each entry in the table represents a single search.
     */
    public static final class HistoryItem implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HISTORY);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of past searches.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single past search.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

        /** Name of database table for searches */
        public final static String TABLE_NAME = "history";

        /**
         * Unique ID number for the search (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the supplement.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPP_NAME ="name";

        /**
         * Description of the supplement.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPP_DESCRIPTION ="description";

        /**
         * Dosage of the supplement.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPP_DOSAGE ="dosage";

        /**
         * Effects of the supplement.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPP_EFFECTS ="effects";
    }
}
