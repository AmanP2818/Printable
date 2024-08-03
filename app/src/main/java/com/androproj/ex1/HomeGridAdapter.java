package com.androproj.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<String> items;
    private final ArrayList<Integer> images;
    private final ArrayList<String> subText;
    private final ArrayList<Integer> favorite;

    public HomeGridAdapter(Context context, ArrayList<String> items, ArrayList<Integer> images, ArrayList<String> subText, ArrayList<Integer> favorite) {
        this.context = context;
        this.items = items;
        this.images = images;
        this.subText = subText;
        this.favorite = favorite;
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
        ImageButton fav = convertView.findViewById(R.id.favorite);

        imageView.setImageResource(images.get(position));
        textView.setText(items.get(position));
        subtexts.setText(subText.get(position));

        // Set initial favorite icon based on the state
        if (favorite.get(position) == R.drawable.favorite_icon_color) {
            fav.setImageResource(R.drawable.favorite_icon_color);
        } else {
            fav.setImageResource(R.drawable.favorite_icon);
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite.get(position) == R.drawable.favorite_icon) {
                    favorite.set(position, R.drawable.favorite_icon_color);
                    fav.setImageResource(R.drawable.favorite_icon_color);
                } else {
                    favorite.set(position, R.drawable.favorite_icon);
                    fav.setImageResource(R.drawable.favorite_icon);
                }
            }
        });

        return convertView;
    }
}
