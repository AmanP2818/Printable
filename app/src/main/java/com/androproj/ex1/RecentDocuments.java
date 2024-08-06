package com.androproj.ex1;

public class RecentDocuments {
    private int imageResource;
    private String title;
    private String subtitle;
    private int iconResource;
    private String path;

    public RecentDocuments(int imageResource, String title, String subtitle, int iconResource, String path) {
        this.imageResource = imageResource;
        this.title = title;
        this.subtitle = subtitle;
        this.iconResource = iconResource;
        this.path = path;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getPath(){
        return path;
    }
}
