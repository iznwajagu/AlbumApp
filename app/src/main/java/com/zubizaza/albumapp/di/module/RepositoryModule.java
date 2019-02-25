package com.zubizaza.albumapp.di.module;

import android.app.Application;
import android.support.annotation.NonNull;

import com.zubizaza.albumapp.api.NetworkApiFactory;
import com.zubizaza.albumapp.data.AlbumDatabase;
import com.zubizaza.albumapp.data.AlbumLocalCache;
import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.data.DataConstants;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
     AlbumLocalCache provideLocalCache(@NonNull Application application) {
        AlbumDatabase database = AlbumDatabase.getInstance(application);
        return new AlbumLocalCache(database.albumDao(), Executors.newSingleThreadExecutor());
    }

    @Provides
     AlbumRepository provideAlbumRepository(@NonNull AlbumLocalCache albumLocalCache ){
        return new AlbumRepository(NetworkApiFactory.create(DataConstants.BASE_URL), albumLocalCache);
    }

}
