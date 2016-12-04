package com.example.supplify2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        new accessNetwork().execute("");

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

    private class accessNetwork extends AsyncTask<String, Void, String> {
        int[] types = {2, 4, 5, 6};
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.side_effects);
        @Override
        protected String doInBackground(String... params) {

            try {
                map = SupplifyScraper.nootrimentScraper(str, types);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> list = map.get("SideEffects");
            String temp = "";
            for (String s : list) {
                temp = temp + s;
            }
            return temp;
        }

        @Override
        protected void onPostExecute(String result) {

            textView1.setText(str);
            textView2.setText(result);

        }

        @Override
        protected void onPreExecute() {
        }


    }
}
