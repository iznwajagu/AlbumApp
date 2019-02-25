package com.zubizaza.albumapp;

import android.app.Application;

import com.zubizaza.albumapp.di.component.AppComponent;
import com.zubizaza.albumapp.di.component.DaggerAppComponent;
import com.zubizaza.albumapp.di.module.AppModule;

public class AlbumApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }



}
