package com.zubizaza.albumapp.di.component;

import com.zubizaza.albumapp.di.module.AppModule;
import com.zubizaza.albumapp.views.AlbumListFragment;
import com.zubizaza.albumapp.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void injectActivity(MainActivity mainActivity);

    void injectFragment(AlbumListFragment albumListFragment);

}
