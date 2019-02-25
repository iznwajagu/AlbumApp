package com.zubizaza.albumapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.data.model.Album;

import java.util.Collections;
import java.util.List;

public class AlbumViewModel extends ViewModel {

    private AlbumRepository albumRepository;

    private MutableLiveData<List<Album>> mAlbumList = new MutableLiveData<>();
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    public MutableLiveData<String> progressBarState = new MutableLiveData<>();


    public AlbumViewModel(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        subscribeToRepositoryAlbums();
        subscribeToNetworkErrors();
    }

    void subscribeToRepositoryAlbums(){

        albumRepository.getAlbumList().observeForever( albumList ->{
            if(albumList!= null && albumList.size() > 0) {
                Collections.sort(albumList);
                mAlbumList.postValue(albumList);
            }
            progressBarState.postValue("hide");
        });

    }

    private void subscribeToNetworkErrors(){
        albumRepository.getNetworkErrors().observeForever( error -> {
            statusMessage.postValue(error.toString());
        });
    }

    public MutableLiveData<List<Album>> getAlbumList() {
        return mAlbumList;
    }

    public MutableLiveData<String> getStatusMessage() {
        return statusMessage;
    }



}
