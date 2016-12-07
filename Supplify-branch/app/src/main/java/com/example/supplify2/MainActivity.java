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

import com.example.supplify2.data.DbHelper;
import com.example.supplify2.data.GlobalState;
import com.example.supplify2.data.HistoryContract.HistoryItem;
import com.example.supplify2.data.Supp;

public class MainActivity extends AppCompatActivity implements
        android.app.LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int HISTORY_LOADER = 0;

    /** Content URI for the existing supplement (null if it's a new supplement) */
    private Uri mCurrentHistoryUri;

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

                // Convert the search text into a string
                String searchName = etLocation.getText().toString();

                // Check if the supplement already exists
                GlobalState state = (GlobalState) getApplicationContext();
                DbHelper db = state.getDatabase();

                Supp newSupp = new Supp(1, searchName, "", "", "");
                //db.insertSupp(newSupp);
//                if(db.suppExist(searchName)) { // if the supplement exists in our database
//                    // retrieve it
//                }
//                else if(true) { // if the supplement is on Nootriment
//                    // scrape it
//                }
//                else { // it doesn't exist anywhere
//                    // display error message
//                }

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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        // Since the editor shows all supplements attributes, define a projection that contains
        // all columns from the supplement table
        String[] projection = {
                HistoryItem._ID,
                HistoryItem.COLUMN_SUPP_NAME,
                HistoryItem.COLUMN_SUPP_DESCRIPTION,
                HistoryItem.COLUMN_SUPP_DOSAGE,
                HistoryItem.COLUMN_SUPP_EFFECTS };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentHistoryUri,     // Query the content URI for the current supplement
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of supplement attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(HistoryItem.COLUMN_SUPP_NAME);
            int descriptionIndex = cursor.getColumnIndex(HistoryItem.COLUMN_SUPP_DESCRIPTION);
            int dosageIndex = cursor.getColumnIndex(HistoryItem.COLUMN_SUPP_DOSAGE);
            int effectsIndex = cursor.getColumnIndex(HistoryItem.COLUMN_SUPP_EFFECTS);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String description = cursor.getString(descriptionIndex);
            int dosage = cursor.getInt(dosageIndex);
            int effects = cursor.getInt(effectsIndex);

            // Update the views on the screen with the values from the database
//            mNameEditText.setText(name);
//            mDescriptionEditText.setText(description);
//            mDosageEditTest.setText(dosage);
//            mEffectsEditText.setText(effects);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // For whatever reason, the Loader's data is now unavailable.
        // Remove any references to the old data by replacing it with
        // a null Cursor.
        mCursorAdapter.swapCursor(null);

        // If the loader is invalidated, clear out all the data from the input fields.
//        mNameEditText.setText("");
//        mDescriptionEditText.setText("");
//        mDosageEditText.setText("");
//        mEffectsSpinner.setSelection(0); // Select "Unknown" gender
    }
}
