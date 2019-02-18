package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.zubizaza.albumapp.api.NetworkApi;
import com.zubizaza.albumapp.data.model.Album;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private NetworkApi networkApi;
    private AlbumLocalCache albumLocalCache;
    public MutableLiveData networkErrors = new MutableLiveData<String>();

    public AlbumRepository(NetworkApi networkApi, AlbumLocalCache albumLocalCache){
        this.networkApi = networkApi;
        this.albumLocalCache = albumLocalCache;
    }

    void fetchAlbum(){

        networkApi.fetchAlbums().enqueue(new Callback<List<Album>>() {

            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()) {
                    List<Album> albums = response.body();
                    savedFetchedAlbums(albums != null? albums : Collections.EMPTY_LIST);
                }else{
                    networkErrors.postValue(response.errorBody()!=null? response.errorBody() : "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                networkErrors.postValue(t.getMessage()!=null? t.getMessage() : "Something went wrong");
            }

        });

    }

    private void savedFetchedAlbums(List<Album> albums){
        albumLocalCache.insertNewAlbums(albums);
    }

    public LiveData<List<Album>> getAlbumList() {
        fetchAlbum();
        return albumLocalCache.fetchAlbumsFromLocalDatabase();
    }

}
