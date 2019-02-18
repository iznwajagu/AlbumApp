package com.zubizaza.albumapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.zubizaza.albumapp.data.AlbumRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AlbumRepository mRepository;

    public ViewModelFactory(AlbumRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AlbumViewModel(mRepository);
    }

}
