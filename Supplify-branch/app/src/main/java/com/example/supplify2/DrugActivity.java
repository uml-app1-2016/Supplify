package com.example.supplify2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.supplify2.R.drawable.favorite;

public class DrugActivity extends AppCompatActivity{
    DrugRepo repo = new DrugRepo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        // our async task runs here
        new accessNetwork().execute("");
        // creating a new repo obj to access db
        DrugRepo repo = new DrugRepo(this);

        // on click listeners for the buttons on the drug activity so that we can toggle the text when they're clicked.
        Button findMagicBtn = (Button) findViewById(R.id.magic_btn1);
        findMagicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicLl = (LinearLayout) findViewById(R.id.magic_layout1);
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
        Button findMagicBtn3 = (Button) findViewById(R.id.magic_btn3);
        findMagicBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicL3 = (LinearLayout) findViewById(R.id.magic_layout3);
                if (findMagicL3.getVisibility() == View.VISIBLE) {
                    findMagicL3.setVisibility(View.GONE);
                } else {
                    findMagicL3.setVisibility(View.VISIBLE);
                }
            }
        });
        Button findMagicBtn4 = (Button) findViewById(R.id.magic_btn4);
        findMagicBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicL4 = (LinearLayout) findViewById(R.id.magic_layout4);
                if (findMagicL4.getVisibility() == View.VISIBLE) {
                    findMagicL4.setVisibility(View.GONE);
                } else {
                    findMagicL4.setVisibility(View.VISIBLE);
                }

            }
        });
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
            case R.id.action_new_favorite_view_history:
                TextView view = (TextView) findViewById(R.id.textView1);
                String name = view.getText().toString();
                Drug drug = new Drug();
                drug.name = name;
                drug.Drug_ID = 0;
                repo.insert(drug);
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            case R.id.action_already_in_history_view_history:
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class accessNetwork extends AsyncTask<String, Void, ArrayList<String>> {
        // these are the types that are passed to our scraper
        // for their meanings view scraper.java
        int[] types = {2, 4, 5, 6};
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.side_effects);
        TextView textView3 = (TextView) findViewById(R.id.pros);
        TextView textView4 = (TextView) findViewById(R.id.cons);
        TextView textView5 = (TextView) findViewById(R.id.dosage);
        ProgressDialog progress = new ProgressDialog(DrugActivity.this);
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            try {
                // passing in the parameters previously created
                map = SupplifyScraper.nootrimentScraper(str, types);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> list = map.get("SideEffects");
            ArrayList<String> listA = new ArrayList<String>(4);
            String temp = "";
            for (String s : list) {
                temp = temp + '\n' +  s + '\n';
            }
            listA.add(0,temp);
            String temp1 = "";
            ArrayList<String> list2 = map.get("Dosage");
            for (String s : list2) {
                temp1 = temp1 + '\n' + s + '\n';
            }
            listA.add(1, temp1);
            String temp2 = "";
            ArrayList<String> list3 = map.get("Pros");
            for (String s : list3) {
                char c = '\u2022';
                temp2 = temp2 + '\n' + c + s + '\n';
            }
            listA.add(2, temp2);
            String temp3 = "";
            ArrayList<String> list4 = map.get("Cons");
            for (String s : list4) {
                char c = '\u2022';
                temp3 = temp3 + '\n' + c + s + '\n';
            }
            listA.add(3, temp3);
            return listA;
        }
        // the post went through successfully over the network.
        @Override
        protected void onPostExecute(ArrayList<String> result) {

            String temp1 = result.get(0);
            String temp2 = result.get(1);
            String temp3 = result.get(2);
            String temp4 = result.get(3);

            if (temp1 != "") {
                View b = findViewById(R.id.magic_btn4);
                b.setVisibility(View.VISIBLE);
            }

            if (temp2 != "") {
                View b = findViewById(R.id.magic_btn3);
                b.setVisibility(View.VISIBLE);
            }

            if (temp3 != "") {
                View b = findViewById(R.id.magic_btn2);
                b.setVisibility(View.VISIBLE);
            }

            if (temp4 != "") {
                View b = findViewById(R.id.magic_btn1);
                b.setVisibility(View.VISIBLE);
            }
            // uppercasing the first letter of our drug word.
            str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
            textView1.setText(str);
            textView2.setText(result.get(0));
            textView3.setText(result.get(2));
            textView4.setText(result.get(3));
            textView5.setText(result.get(1));

            /*Drug d = repo.getDrugByName(textView1.getText().toString());
            if(d == null) {
                ImageView img = (ImageView) findViewById(R.id.action_new_favorite_view_history);
                img.setVisibility(View.VISIBLE);
                ImageView img2 = (ImageView) findViewById(R.id.action_already_in_history_view_history);
                img2.setVisibility(View.INVISIBLE);
            } else {

                ImageView img = (ImageView) findViewById(R.id.action_new_favorite_view_history);
                img.setVisibility(View.INVISIBLE);
                ImageView img2 = (ImageView) findViewById(R.id.action_already_in_history_view_history);
                img2.setVisibility(View.VISIBLE);
            }*/
            progress.dismiss();

        }

        @Override
        protected void onPreExecute() {
            progress.setTitle("Loading");
            progress.setMessage("Retrieiving Supplement Information...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }
}
