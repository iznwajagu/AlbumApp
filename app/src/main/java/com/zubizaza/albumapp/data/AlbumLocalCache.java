package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class AlbumLocalCache {

    private AlbumDao albumDao;
    private ExecutorService iOExecutor;

    @Inject
    public AlbumLocalCache(@NonNull AlbumDao albumDao, @NonNull ExecutorService iOExecutor) {
        this.albumDao = albumDao;
        this.iOExecutor = iOExecutor;
    }


    void insertNewAlbums(List<Album> albums){
        iOExecutor.execute(() -> {
            albumDao.insert(albums);
        });
    }

    LiveData<List<Album>> fetchAlbumsFromLocalDatabase(){
        return albumDao.fetchAlbums();
    }

}
