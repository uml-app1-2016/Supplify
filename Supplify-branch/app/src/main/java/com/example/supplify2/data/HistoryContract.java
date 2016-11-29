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

    public static final String CONTENT_AUTHORITY = "com.example.android.history";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HISTORY = "HISTORY";

    public static final class HistoryItem implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HISTORY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY;

        /** Name of database table for searches */
        public final static String TABLE_NAME = "history";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_SUPP_NAME ="name";

        public final static String COLUMN_SUPP_DESCRIPTION ="description";

        public final static String COLUMN_SUPP_DOSAGE ="dosage";

        public final static String COLUMN_SUPP_EFFECTS ="effects";
    }
}
