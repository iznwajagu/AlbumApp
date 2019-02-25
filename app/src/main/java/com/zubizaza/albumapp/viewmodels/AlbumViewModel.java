package com.zubizaza.albumapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

public class AlbumViewModel extends ViewModel {

    private AlbumRepository albumRepository;
    private LiveData<List<Album>> mAlbumList;
    public MutableLiveData<String> statusMessage = new MutableLiveData<>();

    public AlbumViewModel(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        subscribeToRepositoryAlbums();
        subscribeToNetworkErrors();
    }

    void subscribeToRepositoryAlbums(){
        mAlbumList = albumRepository.getAlbumList();
    }

    public LiveData<List<Album>> publishAlbumsToSubsribers() {
        return mAlbumList;
    }

    private void subscribeToNetworkErrors(){
        albumRepository.networkErrors.observeForever( error -> {
            statusMessage.postValue(error.toString());
        });
    }


}
