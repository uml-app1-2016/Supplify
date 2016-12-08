package com.example.supplify2;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.supplify2.data.DbHelper;
import com.example.supplify2.data.GlobalState;
import com.example.supplify2.data.HistoryContract;
import com.example.supplify2.data.Supp;

/**
 * Created by George on 11/17/2016.
 */
public class HistoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the suppplement data loader */
    private static final int SUPP_LOADER = 0;

    /** Adapter for the ListView */
    HistoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        GlobalState state = (GlobalState) getApplicationContext();

        DbHelper db = state.getDatabase();
        //final Supp supp = db.getSuppByName("name"); // WRONG STRING!

        // Find the ListView which will be populated with the history data
        ListView historyListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        historyListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of history data in the Cursor.
        // There is no supplement data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new HistoryCursorAdapter(this, null);
        historyListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(HistoryActivity.this, DrugActivity.class);

                // Form the content URI that represents the specific supplement that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link HistoryEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.history/item/2"
                // if the supplement with ID 2 was clicked on.
                Uri currentSuppUri = ContentUris.withAppendedId(HistoryContract.HistoryItem.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentSuppUri);

                // Launch the {@link EditorActivity} to display the data for the current supp.
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(SUPP_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                HistoryContract.HistoryItem._ID,
                HistoryContract.HistoryItem.COLUMN_SUPP_NAME};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,                       // Parent activity context
                HistoryContract.HistoryItem.CONTENT_URI,    // Provider content URI to query
                projection,                                 // Columns to include in the resulting Cursor
                null,                                       // No selection clause
                null,                                       // No selection arguments
                null);                                      // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link HistoryCursorAdapter} with this new cursor containing updated supplement data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}