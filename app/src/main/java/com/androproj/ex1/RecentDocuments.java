package com.androproj.ex1;

public class RecentDocuments {
    private int imageResource;
    private String title;
    private String subtitle;
    private int iconResource;

    public RecentDocuments(int imageResource, String title, String subtitle, int iconResource) {
        this.imageResource = imageResource;
        this.title = title;
        this.subtitle = subtitle;
        this.iconResource = iconResource;
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
}
