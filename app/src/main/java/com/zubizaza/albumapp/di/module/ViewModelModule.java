package com.zubizaza.albumapp.di.module;


import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.zubizaza.albumapp.data.AlbumRepository;
import com.zubizaza.albumapp.viewmodels.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelProvider.Factory providesViewModelFactory(@NonNull AlbumRepository albumRepository){
        return new ViewModelFactory(albumRepository);
    }

}
