package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.zubizaza.albumapp.api.NetworkApi;
import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

public class AlbumRepository {

    private NetworkApi networkApi;
    private AlbumLocalCache albumLocalCache;
    public MutableLiveData networkErrors = new MutableLiveData<String>();

    public AlbumRepository(NetworkApi networkApi, AlbumLocalCache albumLocalCache){
        this.networkApi = networkApi;
        this.albumLocalCache = albumLocalCache;
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
