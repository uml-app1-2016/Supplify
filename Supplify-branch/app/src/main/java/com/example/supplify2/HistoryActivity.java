package com.example.supplify2;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.supplify2.data.HistoryContract;

/**
 * Created by George on 11/17/2016.
 */
public class HistoryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int HISTORY_LOADER = 0;

    /** Adapter for the ListView */
    HistoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Find the ListView which will be populated with the history data
        ListView historyListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        historyListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of history data in the Cursor.
        // There is no history data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new HistoryCursorAdapter(this, null);
        historyListView.setAdapter(mCursorAdapter);

        // When the user clicks on an item in the list of Favorite Supps, bring him to that supplement...
        // Setup the item click listener
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link DrugActivity}
                Intent intent = new Intent(HistoryActivity.this, DrugActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(HistoryContract.HistoryItem.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(HISTORY_LOADER, null, this);
    }


//        // Grab the intent that was used to launch this activity
//        Intent intent = getIntent();
//
//        // Store the drug's name  in a string
//        String suppName = intent.getStringExtra("location");
//        String suppDescription = "description";
//        String suppDosage = "dosage";
//        String suppEffects = "effects";
//
//        // Create a ContentValues object where column names are the keys,
//        // and pet attributes from the editor are the values.
//        ContentValues values = new ContentValues();
//        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_NAME, suppName);
//        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_DESCRIPTION, suppDescription);
//        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_DOSAGE, suppDosage);
//        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_EFFECTS, suppEffects);
//    }


    // Functions needed for a functional Cursor!
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                HistoryContract.HistoryItem._ID,
                HistoryContract.HistoryItem.COLUMN_SUPP_NAME };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                HistoryContract.HistoryItem.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
