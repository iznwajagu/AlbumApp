package com.zubizaza.albumapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zubizaza.albumapp.api.NetworkApi;
import com.zubizaza.albumapp.data.model.Album;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private NetworkApi networkApi;
    private AlbumLocalCache albumLocalCache;
    public MutableLiveData networkErrors = new MutableLiveData<String>();

    @Inject
    public AlbumRepository(@NonNull NetworkApi networkApi, @NonNull AlbumLocalCache albumLocalCache){
        this.networkApi = networkApi;
        this.albumLocalCache = albumLocalCache;
    }

    void fetchAlbumsFromNetwork(){

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
        //fetch new set of albums from network
        fetchAlbumsFromNetwork();

        //subscribe to receive albums from local room database
        return albumLocalCache.fetchAlbumsFromLocalDatabase();
    }

}
