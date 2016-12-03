package com.example.supplify2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supplify2.data.HistoryContract;

/**
 * Created by daniel on 11/16/16.
 */
public class DrugActivity extends AppCompatActivity {

    /** Content URI for the existing pet (null if it's a new pet) */
    private Uri mCurrentSuppUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        TextView textView1 = (TextView) findViewById(R.id.textView1);

        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        textView1.setText(str);

        Button findMagicBtn = (Button) findViewById(R.id.magic_btn);
        findMagicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicLl = (LinearLayout) findViewById(R.id.magic_layout);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });
        Button findMagicBtn2 = (Button) findViewById(R.id.magic_btn2);
        findMagicBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicL2 = (LinearLayout) findViewById(R.id.magic_layout2);
                if (findMagicL2.getVisibility() == View.VISIBLE) {
                    findMagicL2.setVisibility(View.GONE);
                } else {
                    findMagicL2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Get user input from editor and save pet into database.
     */
    private void saveSupp() {
        // Read from the (already) scraped page
        TextView viewStringName;
        viewStringName = (TextView) findViewById(R.id.textView1);
        String nameString = viewStringName.getText().toString();

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentSuppUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(breedString) &&
                TextUtils.isEmpty(weightString) && mGender == PetEntry.GENDER_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and supp attributes from the page are the values.
        ContentValues values = new ContentValues();
        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_NAME, nameString);
        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_DESCRIPTION, "Get lit.");
        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_DOSAGE, "As much as your body can possibly handle.");
        values.put(HistoryContract.HistoryItem.COLUMN_SUPP_EFFECTS, "Head temporarily shrinks to the size of a tennis ball");

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentPetUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentPetUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId())
        {
            // Respond to a lick on the "Home" menu option
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            // Respond to a click on the "View history" menu option
            case R.id.action_view_history:
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
