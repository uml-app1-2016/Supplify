package com.example.supplify2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supplify2.data.DbHelper;
import com.example.supplify2.data.Supp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daniel on 11/16/16.
 */



public class DrugActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        Intent intent = getIntent();
        String str = intent.getStringExtra("location");


        if(MainActivity.db.suppExist(str)) {
            Supp supp = MainActivity.db.getSuppByName(str);
            String name = supp.getName();
            String dosage = supp.getDosage();
            String sideEffects = supp.getEffect();
            String description = supp.getDescription();
            TextView textView1 = (TextView) findViewById(R.id.textView1);
            TextView textView2 = (TextView) findViewById(R.id.side_effects);
            TextView textView3 = (TextView) findViewById(R.id.pros);
            TextView textView4 = (TextView) findViewById(R.id.cons);
            TextView textView5 = (TextView) findViewById(R.id.dosage);
            textView1.setText(name);
            textView2.setText(dosage);
            textView3.setText(description);
            textView4.setText(description);
            textView5.setText(sideEffects);
        }
        else {
            new accessNetwork().execute("");
        }
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_drug, menu);
        return true;
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


            // User clicked on a menu option in the app bar overflow menu

            // Respond to a click on the "Add to favorites" menu option
            case R.id.action_set_favorite:
                //Add to database
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private class accessNetwork extends AsyncTask<String, Void, ArrayList<String>> {
        int[] types = {2, 4, 5, 6};
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.side_effects);
        TextView textView3 = (TextView) findViewById(R.id.pros);
        TextView textView4 = (TextView) findViewById(R.id.cons);
        TextView textView5 = (TextView) findViewById(R.id.dosage);


        @Override
        protected ArrayList<String> doInBackground(String... params) {

            try {
                map = SupplifyScraper.nootrimentScraper(str, types);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> list = map.get("SideEffects");
            ArrayList<String> listA = new ArrayList<String>(4);
            String temp = "";
            for (String s : list) {
                temp = temp + s;

            }

            listA.add(0,temp);
            String temp1 = "";
            ArrayList<String> list2 = map.get("Dosage");
            for (String s : list2) {
                temp1 = temp1 + s;
            }
            listA.add(1, temp1);
            String temp2 = "";
            ArrayList<String> list3 = map.get("Pros");
            for (String s : list3) {
                temp2 = temp2 + s;
            }
            listA.add(2, temp2);
            String temp3 = "";
            ArrayList<String> list4 = map.get("Cons");
            for (String s : list4) {
                temp3 = temp3 + s;
            }
            listA.add(3, temp3);
            return listA;
        }

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
                View b = findViewById(R.id.magic_btn);
                b.setVisibility(View.VISIBLE);
            }

            textView1.setText(str);
            textView2.setText(result.get(0));
            textView3.setText(result.get(2));
            textView4.setText(result.get(3));
            textView5.setText(result.get(1));

        }

        @Override
        protected void onPreExecute() {
        }


    }
}
