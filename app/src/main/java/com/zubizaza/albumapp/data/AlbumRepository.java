package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

public class AlbumRepository {

    public AlbumRepository(){

    }

    void fetchAlbum(){

        savedFetchedAlbums(null);

    }

    private void savedFetchedAlbums(List<Album> albums){

    }

    public LiveData<List<Album>> getAlbumList() {
        fetchAlbum();
        return null;
    }

}
