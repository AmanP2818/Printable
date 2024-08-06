package com.androproj.ex1;

public class Folders {
    private final String title;
    private final int images;
    private final String subText;
    private Boolean favorite;
    private final String path;

    public Folders(String title, int images, String subText, Boolean favorite, String path) {
        this.title = title;
        this.images = images;
        this.subText = subText;
        this.favorite = favorite;
        this.path = path;
    }

    public String getTitle(){
        return title;
    }

    public int getImages() {
        return images;
    }

    public String getSubText() {
        return subText;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public String getPath() {
        return path;
    }

    public void setFavorite(Boolean counter){
        favorite = counter;
    }
}
