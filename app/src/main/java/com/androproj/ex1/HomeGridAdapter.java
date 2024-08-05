package com.androproj.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<String> items;
    private final ArrayList<Integer> images;
    private final ArrayList<String> subText;
    private final ArrayList<Boolean> favorite;
    private final ArrayList<String> path;

    public HomeGridAdapter(Context context, ArrayList<String> items, ArrayList<Integer> images, ArrayList<String> subText, ArrayList<Boolean> favorite, ArrayList<String> path) {
        this.context = context;
        this.items = items;
        this.images = images;
        this.subText = subText;
        this.favorite = favorite;
        this.path = path;
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
        if (favorite.get(position)) {
            fav.setImageResource(R.drawable.favorite_icon_color); // Replace with your favorite icon resource
        } else {
            fav.setImageResource(R.drawable.favorite_icon); // Replace with your non-favorite icon resource
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav = favorite.get(position);
                String itemName = items.get(position);
                File directory = new File(context.getExternalFilesDir(null), itemName);
                File favFile = new File(directory, ".favorite");

                if (isFav) {
                    // If currently favorite, remove favorite
                    if (favFile.exists()) {
                        boolean result = favFile.delete();
                        if (result) {
                            favorite.set(position, false);
                            fav.setImageResource(R.drawable.favorite_icon);
                            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to remove favorite", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // If not favorite, add favorite
                    try {
                        boolean result = favFile.createNewFile();
                        if (result) {
                            favorite.set(position, true);
                            fav.setImageResource(R.drawable.favorite_icon_color);
                            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to add favorite", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error adding favorite", Toast.LENGTH_SHORT).show();
                    }
                }

                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
