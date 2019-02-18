package com.zubizaza.albumapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

public class AlbumViewModel extends ViewModel {

    private AlbumRepository albumRepository;
    private LiveData<List<Album>> mAlbumList;

    AlbumViewModel(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
        subscribeToRepositoryAlbums();
    }

    void subscribeToRepositoryAlbums(){
        mAlbumList = albumRepository.getAlbumList();
    }

    public LiveData<List<Album>> publishAlbumsToSubsribers() {
        return mAlbumList;
    }


}
