package com.example.supplify2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.supplify2.data.HistoryContract.HistoryItem;

public class MainActivity extends AppCompatActivity
//        implements LoaderManager.LoaderCallbacks<Cursor>
{

    /** Identifier for the pet data loader */
    private static final int HISTORY_LOADER = 0;

    /** Adapter for the ListView */
    HistoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Find the ListView which will be populated with the past searched supplement data
//        ListView historyListView = (ListView) findViewById(R.id.list);
//
//        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
//        View emptyView = findViewById(R.id.empty_view);
//        historyListView.setEmptyView(emptyView);
//
//        // Setup an Adapter to create a list item for each row of supplement data in the Cursor.
//        // There is no supplement data yet (until the loader finishes) so pass in null for the Cursor.
//        mCursorAdapter = new HistoryCursorAdapter(this, null);
//        historyListView.setAdapter(mCursorAdapter);
//
//        // Setup the item click listener to go to history view
//        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                // Create new intent to go to {@link EditorActivity}
//                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
//
//                // Form the content URI that represents the specific pet that was clicked on,
//                // by appending the "id" (passed as input to this method) onto the
//                // {@link PetEntry#CONTENT_URI}.
//                // For example, the URI would be "content://com.example.android.pets/pets/2"
//                // if the pet with ID 2 was clicked on.
//                Uri currentPetUri = ContentUris.withAppendedId(HistoryContract.HistoryItem.CONTENT_URI, id);
//
//                // Set the URI on the data field of the intent
//                intent.setData(currentPetUri);
//
//                // Launch the {@link EditorActivity} to display the data for the current pet.
//                startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId())
        {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_view_history:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertSupp()
    {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HistoryItem.COLUMN_SUPP_NAME, "Sunlight");
        values.put(HistoryItem.COLUMN_SUPP_DESCRIPTION, "Dat loud loud");
        values.put(HistoryItem.COLUMN_SUPP_DOSAGE, "As much and often as possible");
        values.put(HistoryItem.COLUMN_SUPP_EFFECTS, "Happiness");

        // Insert a new row for Sunlight into the provider using the ContentResolver.
        // Use the {@link HistoryItem#CONTENT_URI} to indicate that we want to insert
        // into the history database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(HistoryItem.CONTENT_URI, values);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteHistory()
    {
        int rowsDeleted = getContentResolver().delete(HistoryItem.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from history database");
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
//    {
//        // Define a projection that specifies the columns from the table we care about.
//        String[] projection = {
//                HistoryItem._ID,
//                HistoryItem.COLUMN_SUPP_NAME };
//
//        // This loader will execute the ContentProvider's query method on a background thread
//        return new CursorLoader(this,      // Parent activity context
//                HistoryItem.CONTENT_URI,   // Provider content URI to query
//                projection,                // Columns to include in the resulting Cursor
//                null,                      // No selection clause
//                null,                      // No selection arguments
//                null);                     // Default sort order
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
//    {
//        // Update {@link HistoryCursorAdapter} with this new cursor containing updated pet data
//        mCursorAdapter.swapCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader)
//    {
//        // Callback called when the data needs to be deleted
//        mCursorAdapter.swapCursor(null);
//    }
}
