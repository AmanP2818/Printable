package com.androproj.ex1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String enteredPno;

    private GridView gridView;
    private ListView listView;
    private String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
    private int[] images = {R.drawable.scan_export_icon, R.drawable.scan_id, R.drawable.scan_logo, R.drawable.scan_more_icon};
    private TextView gridVisible;
    private TextView listVisible;

    private HomeListAdapter homeListAdapter;
    private List<RecentDocuments> itemList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enteredPno = getIntent().getStringExtra("mobile");

        gridVisible = findViewById(R.id.gridVisible);

        if(items.length>0){
            gridVisible.setText("");

        }
        gridView = findViewById(R.id.gridView);
        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(this, items, images,null);
        gridView.setAdapter(homeGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Assuming HomeGridAdapter.getItem() returns an item with a getTitle() method
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        itemList = new ArrayList<>();
        itemList.add(new RecentDocuments(R.drawable.scan_export_icon, "Title 1", "Subtitle 1", R.drawable.favorite_icon));
        itemList.add(new RecentDocuments(R.drawable.scan_id, "Title 2", "Subtitle 2", R.drawable.favorite_icon));
        itemList.add(new RecentDocuments(R.drawable.scan_logo, "Title 3", "Subtitle 3", R.drawable.favorite_icon));
        itemList.add(new RecentDocuments(R.drawable.scan_more_icon, "Title 4", "Subtitle 4", R.drawable.favorite_icon));

        listView = findViewById(R.id.listview);
        homeListAdapter = new HomeListAdapter(this, itemList);
        listView.setAdapter(homeListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentDocuments clickedItem = (RecentDocuments) homeListAdapter.getItem(position);
                Toast.makeText(MainActivity.this, "Clicked: " + clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}