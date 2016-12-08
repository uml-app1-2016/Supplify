package com.example.supplify2;

import android.animation.Animator;
import android.app.ActivityOptions;
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
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.supplify2.data.DbHelper;
import com.example.supplify2.data.GlobalState;
import com.example.supplify2.data.HistoryContract.HistoryItem;
import com.example.supplify2.data.Supp;

public class MainActivity extends AppCompatActivity {

    /**
     * Identifier for the pet data loader
     */
    private static final int HISTORY_LOADER = 0;

    /**
     * Content URI for the existing supplement (null if it's a new supplement)
     */
    private Uri mCurrentHistoryUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnGo = (Button) findViewById(R.id.go_to_drugs);

        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText etLocation = (EditText) findViewById(R.id.search_target);

                // Convert the search text into a string
                String searchName = etLocation.getText().toString();


                Intent intent = new Intent(MainActivity.this, DrugActivity.class);
                intent.putExtra("location", etLocation.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View history" menu option
            case R.id.action_view_history:
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}