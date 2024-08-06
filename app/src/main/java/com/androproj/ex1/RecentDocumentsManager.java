package com.androproj.ex1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class RecentDocumentsManager {
    private static final String PREFS_NAME = "recent_docs_prefs";
    private static final String RECENT_DOCS_KEY = "recent_docs";
    private static final int MAX_RECENT_DOCS = 4;

    private SharedPreferences sharedPreferences;

    public RecentDocumentsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addDocument(String documentPath) {
        List<String> recentDocs = getRecentDocuments();

        // Remove the document if it's already in the list
        recentDocs.remove(documentPath);
        // Add the document at the top
        recentDocs.add(0, documentPath);

        // Keep only the latest MAX_RECENT_DOCS
        if (recentDocs.size() > MAX_RECENT_DOCS) {
            recentDocs = recentDocs.subList(0, MAX_RECENT_DOCS);
        }

        saveRecentDocuments(recentDocs);
    }

    public List<String> getRecentDocuments() {
        String recentDocsJson = sharedPreferences.getString(RECENT_DOCS_KEY, "[]");
        return new Gson().fromJson(recentDocsJson, new TypeToken<List<String>>() {}.getType());
    }

    private void saveRecentDocuments(List<String> recentDocs) {
        String recentDocsJson = new Gson().toJson(recentDocs);
        sharedPreferences.edit().putString(RECENT_DOCS_KEY, recentDocsJson).apply();
    }
}

