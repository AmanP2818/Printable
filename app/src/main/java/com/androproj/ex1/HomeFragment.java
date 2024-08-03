package com.androproj.ex1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int REQUEST_PERMISSION_CODE = 100;
    private static final String TAG = "MainActivity";

    String enteredPno;
    Button add_folders;
    private GridView gridView;
    private ArrayList<String> items;
    private ArrayList<Integer> images;
    private ArrayList<String> subText;
    private ArrayList<Integer> fav;
    private TextView gridVisible;
    private HomeGridAdapter homeGridAdapter;
    private HomeListAdapter homeListAdapter;
    private List<RecentDocuments> itemList;
    private ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridVisible = view.findViewById(R.id.gridVisible);

        items = new ArrayList<>();
        images = new ArrayList<>();
        subText = new ArrayList<>();
        fav = new ArrayList<>();

        add_folders = view.findViewById(R.id.add_folders);
        add_folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFolderDialog();
            }
        });

        gridView = view.findViewById(R.id.gridView);
        homeGridAdapter = new HomeGridAdapter(getContext(), items, images, subText,fav);
        gridView.setAdapter(homeGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        itemList = new ArrayList<>();
        itemList.add(new RecentDocuments(R.drawable.pdf, "Title 1", "Subtitle 1", R.drawable.more));
        itemList.add(new RecentDocuments(R.drawable.excel, "Title 2", "Subtitle 2", R.drawable.more));
        itemList.add(new RecentDocuments(R.drawable.excel, "Title 3", "Subtitle 3", R.drawable.more));
        itemList.add(new RecentDocuments(R.drawable.pdf, "Title 4", "Subtitle 4", R.drawable.more));

        listView = view.findViewById(R.id.listview);
        homeListAdapter = new HomeListAdapter(getContext(), itemList);
        listView.setAdapter(homeListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentDocuments clickedItem = (RecentDocuments) homeListAdapter.getItem(position);
                Toast.makeText(getContext(), "Clicked: " + clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;


    }

    private void showAddFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add New Folder");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String folderName = input.getText().toString();
                createAndAddFolder(folderName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void createAndAddFolder(String name) {
//        File mainDir = Environment.getExternalStorageDirectory();
//        if (mainDir != null) {
//            File newFolder = new File(mainDir, name);
//            if (!newFolder.exists()) {
//                if (newFolder.mkdir()) {
//                    items.add(name);
//                    images.add(R.drawable.scan_export_icon); // Replace with an appropriate icon
//                    homeGridAdapter.notifyDataSetChanged();
//                    gridVisible.setText("");
//                } else {
//                    Toast.makeText(this, "Failed to create folder", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "Folder already exists", Toast.LENGTH_SHORT).show();
//            }
//        }
        images.add(R.drawable.folders);
        items.add(name);
        subText.add("Size 5MB");
        fav.add(R.drawable.favorite_icon);
        homeGridAdapter.notifyDataSetChanged();
    }
}