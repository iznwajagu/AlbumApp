package com.zubizaza.albumapp.di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module(includes = { RepositoryModule.class, ViewModelModule.class })
public class AppModule {

    Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    Application providesApplication(){
        return application;
    }

}
