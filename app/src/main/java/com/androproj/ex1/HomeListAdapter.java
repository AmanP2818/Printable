package com.androproj.ex1;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private List<RecentDocuments> items;

    public HomeListAdapter(Context context, List<RecentDocuments> items) {
        this.context = context;
        this.items = items;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_component, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemTitle = convertView.findViewById(R.id.item_title);
        TextView itemSubtitle = convertView.findViewById(R.id.item_subtitle);
        ImageView itemIcon = convertView.findViewById(R.id.item_icon);

        RecentDocuments item = items.get(position);

        itemImage.setImageResource(item.getImageResource());
        itemTitle.setText(item.getTitle());
        itemSubtitle.setText(item.getSubtitle());
        itemIcon.setImageResource(item.getIconResource());

        itemImage.setFocusable(false);
        itemTitle.setFocusable(false);
        itemSubtitle.setFocusable(false);
        itemIcon.setFocusable(false);

        return convertView;
    }
}

