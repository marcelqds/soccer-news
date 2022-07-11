package com.mqds.soccernews.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;


@Entity(primaryKeys = {"title","link"})
public class News {

    @NonNull
    private String title;
    @NonNull
    private String description;
    private String image;
    @NonNull
    private String link;
    private boolean favorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return   " | " +title + " | " +favorite+ " |\n ";
    }
}
