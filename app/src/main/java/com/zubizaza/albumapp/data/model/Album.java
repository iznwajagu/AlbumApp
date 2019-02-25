package com.zubizaza.albumapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "albumdb", indices = {@Index(value = {"id"}, unique = true)})
public class Album implements  Comparable<Album>{

    @PrimaryKey
    @NonNull
    private String userId;
    private String id;
    private String title;

    public Album(String userId, String id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int compareTo(Album album) {
        return this.title.toUpperCase().compareTo(album.getTitle().toUpperCase());
    }

}
