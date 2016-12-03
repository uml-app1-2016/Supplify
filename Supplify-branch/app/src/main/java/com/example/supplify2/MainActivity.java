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
import android.widget.Button;
import android.widget.EditText;
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

        // Find the ListView which will be populated with the past searched supplement data
        final Button historyButton = (Button) findViewById(R.id.go_to_history);

        // Setup the item click listener to go to history view
        historyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });
        final Button btnGo = (Button) findViewById(R.id.go_to_drugs);

        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText etLocation = (EditText) findViewById(R.id.search_target);
                Intent intent = new Intent(MainActivity.this, DrugActivity.class);
                intent.putExtra("location", etLocation.getText().toString());
                startActivity(intent);
            }
        });
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
            // Respond to a click on the "View history" menu option
            case R.id.action_view_history:
                startActivity(new Intent(this, HistoryActivity.class));
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
