package com.zubizaza.albumapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.zubizaza.albumapp.data.model.Album;

import javax.inject.Singleton;

@Singleton
@Database(entities = {Album.class}, version = 1 , exportSchema = false)
public abstract class AlbumDatabase extends RoomDatabase {

    private static final String LOG_TAG = AlbumDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "albumdb";


    private static final Object LOCK = new Object();
    private static AlbumDatabase sInstance;

    public static AlbumDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AlbumDatabase.class, AlbumDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract AlbumDao albumDao();


}