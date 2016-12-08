package com.example.supplify2;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.supplify2.data.DbHelper;
import com.example.supplify2.data.GlobalState;
import com.example.supplify2.data.HistoryContract;
import com.example.supplify2.data.Supp;

import java.util.ArrayList;

public class


HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        DrugRepo repo = new DrugRepo(this);

        final ArrayList<String> list = repo.getDrugNames();
        ArrayList<Drug> drugs = new ArrayList<>();

        DrugAdaptor adaptor = new DrugAdaptor(this, list);

        ListView listview = (ListView) findViewById(R.id.list);

        listview.setAdapter(adaptor);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {



                // Get the {@link Word} object at the given position the user clicked on
                String str = list.get(position);



                // Convert the search text into a string
              ;

                Intent intent = new Intent(HistoryActivity.this, DrugActivity.class);
                intent.putExtra("location", str);
                startActivity(intent);
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.


            }
        });
    }
}