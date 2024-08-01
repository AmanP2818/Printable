package com.androproj.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private final String[] items;
    private final int[] images;
    private final String[] subText;

    public HomeGridAdapter(Context context, String[] items, int[] images, String[] subText) {
        this.context = context;
        this.items = items;
        this.images = images;
        this.subText = subText;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_grid_component, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.maintext);
        TextView subtexts = convertView.findViewById(R.id.subtext);

        imageView.setImageResource(images[position]);
        textView.setText(items[position]);


        return convertView;
    }
}
