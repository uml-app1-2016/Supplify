package com.example.supplify2;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private class accessNetwork extends AsyncTask<String, Void, ArrayList<String>> {
        int[] types = {2, 4, 5, 6};
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        //LinearLayout magic1 = (LinearLayout) findViewById(R.id.magic_layout);
        //LinearLayout magic2 = (LinearLayout) findViewById(R.id.magic_layout2);
        //LinearLayout magic3 = (LinearLayout) findViewById(R.id.magic_layout3);
        LinearLayout magic4 = (LinearLayout) findViewById(R.id.magic_layout4);
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


            textView1.setText(str);

            String temp = result.get(0);
            int length = temp.length();
            length = length / 54;
            int newHeight = length * 50;
            //RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, newHeight);
            //magic4.setLayoutParams(params1);
            textView2.setText(temp);
            textView3.setText(result.get(2));
            textView4.setText(result.get(3));
            textView5.setText(result.get(1));

        }

        @Override
        protected void onPreExecute() {
        }


    }
}
