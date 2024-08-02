package com.androproj.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<String> items;
    private final ArrayList<Integer> images;
    private final ArrayList<String> subText;

    public HomeGridAdapter(Context context, ArrayList<String> items, ArrayList<Integer> images, ArrayList<String> subText) {
        this.context = context;
        this.items = items;
        this.images = images;
        this.subText = subText;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < 0 || position >= items.size() || position >= images.size()) {
            // Handle invalid index gracefully
            return convertView;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_grid_component, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.maintext);
        TextView subtexts = convertView.findViewById(R.id.subtext);

        imageView.setImageResource(images.get(position));
        textView.setText(items.get(position));
        subtexts.setText(subText.get(position));


        return convertView;
    }
}
