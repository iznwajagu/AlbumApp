package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class AlbumLocalCache {

    private AlbumDao albumDao;
    private ExecutorService iOExecutor;

    public AlbumLocalCache(AlbumDao albumDao, ExecutorService iOExecutor) {
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
