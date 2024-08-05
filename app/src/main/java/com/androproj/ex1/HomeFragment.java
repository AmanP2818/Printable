package com.androproj.ex1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
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

import java.io.File;
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
    private ArrayList<Boolean> fav;
    private ArrayList<String> path;

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
        requestStoragePermissions();

        items = new ArrayList<>();
        images = new ArrayList<>();
        subText = new ArrayList<>();
        fav = new ArrayList<>();
        path = new ArrayList<>();

        add_folders = view.findViewById(R.id.add_folders);
        add_folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFolderDialog();
            }
        });

        gridView = view.findViewById(R.id.gridView);
        homeGridAdapter = new HomeGridAdapter(getContext(), items, images, subText,fav,path);
        gridView.setAdapter(homeGridAdapter);

        loadDirectories();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
                return true;
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

    private void loadDirectories() {
        File rootDir = requireContext().getExternalFilesDir(null);
        File[] files = rootDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    long size = getDirectorySize(file);
                    String sizeInString = android.text.format.Formatter.formatFileSize(getContext(), size);
                    items.add(file.getName());
                    subText.add(sizeInString);
                    images.add(R.drawable.folders);
                    path.add(file.getAbsolutePath());
                    File favFile = new File(file, ".favorite");
                    fav.add(favFile.exists());
                }
            }
        }
        if(!items.isEmpty()){
            gridVisible.setText("");
        }else{
            gridVisible.setText("No folders added yet!");
        }
        homeGridAdapter.notifyDataSetChanged();
    }


    private void requestStoragePermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void createAndAddFolder(String name) {
        File directory = new File(requireContext().getExternalFilesDir(null), name);
        if (!directory.exists()) {
            boolean result = directory.mkdir();
            if (result) {
                Toast.makeText(requireContext(), "Directory created successfully", Toast.LENGTH_SHORT).show();
                long size = getDirectorySize(directory);
                String sizeString = android.text.format.Formatter.formatFileSize(getContext(), size);
                images.add(R.drawable.folders);
                items.add(name);
                subText.add(sizeString);
                fav.add(false);
                if(!items.isEmpty()){
                    gridVisible.setText("");
                }else{
                    gridVisible.setText("No folders added yet!");
                }
                homeGridAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(requireContext(), "Failed to create directory", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Directory already exists", Toast.LENGTH_SHORT).show();
        }

    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Folder");
        builder.setMessage("Are you sure you want to delete this folder?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDirectory(position);
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

    private void deleteDirectory(int position) {
        String directoryName = items.get(position);
        File directory = new File(requireContext().getExternalFilesDir(null), directoryName);
        if (directory.exists() && directory.isDirectory()) {
            boolean result = deleteRecursively(directory);
            if (result) {
                Toast.makeText(requireContext(), "Directory deleted successfully", Toast.LENGTH_SHORT).show();
                items.remove(position);
                images.remove(position);
                fav.remove(position);
                subText.remove(position);
                if(!items.isEmpty()){
                    gridVisible.setText("");
                }else{
                    gridVisible.setText("No folders added yet!");
                }
                homeGridAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(requireContext(), "Failed to delete directory", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Directory not found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        return file.delete();
    }
    private long getDirectorySize(File directory) {
        long size = 0;
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else if (file.isDirectory()) {
                        size += getDirectorySize(file);
                    }
                }
            }
        } else if (directory.isFile()) {
            size += directory.length();
        }
        return size;
    }




}