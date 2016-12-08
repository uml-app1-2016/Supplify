package com.example.supplify2;

/**
 * Created by George on 11/17/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugAdaptor extends ArrayAdapter<String> {

    public DrugAdaptor(Context context, ArrayList<String> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        String str = getItem(position);

        TextView textview1 = (TextView) listItemView.findViewById(R.id.name);

        textview1.setText((str));


        return listItemView;
    }

}
