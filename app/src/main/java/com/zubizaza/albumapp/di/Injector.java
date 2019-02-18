package com.zubizaza.albumapp.di;

import android.content.Context;

import com.zubizaza.albumapp.api.NetworkApiFactory;
import com.zubizaza.albumapp.data.AlbumDatabase;
import com.zubizaza.albumapp.data.AlbumLocalCache;
import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.viewmodels.ViewModelFactory;

import java.util.concurrent.Executors;

public class Injector {

    private static AlbumLocalCache provideLocalCache(Context context) {
        AlbumDatabase database = AlbumDatabase.getInstance(context);
        return new AlbumLocalCache(database.albumDao(), Executors.newSingleThreadExecutor());
    }

    private static AlbumRepository provideAlbumRepository(Context context){
        return new AlbumRepository(NetworkApiFactory.create(), provideLocalCache(context));
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        AlbumRepository albumRepository = provideAlbumRepository(context);
        return new ViewModelFactory(albumRepository);
    }


}
