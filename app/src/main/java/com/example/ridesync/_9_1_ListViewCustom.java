package com.example.ridesync;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class _9_1_ListViewCustom extends BaseAdapter {

    Activity activity;
    String[] cardData;
    Integer[] image;

    public _9_1_ListViewCustom(Activity activity, String[] cardData, Integer[] image) {
        this.activity = activity;
        this.cardData = cardData;
        this.image = image;
    }

    @Override
    public int getCount() {
        return cardData.length;
    }

    @Override
    public Object getItem(int position) {
        return cardData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout._listviewmenuoptions, null);
        }

        TextView textLeftCard = convertView.findViewById(R.id.textcard);
        ImageView imageLeftCard = convertView.findViewById(R.id.imagecard);
        CardView cardLeft = convertView.findViewById(R.id.cardView);

        textLeftCard.setText(cardData[position]);
        imageLeftCard.setImageResource(image[position]);


        return convertView;
    }


}
