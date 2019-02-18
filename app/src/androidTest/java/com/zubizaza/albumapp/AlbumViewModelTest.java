package com.zubizaza.albumapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.zubizaza.albumapp.data.AlbumDao;
import com.zubizaza.albumapp.data.AlbumDatabase;
import com.zubizaza.albumapp.data.model.Album;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class AlbumViewModelTest {

    private AlbumDatabase mAlbumDatabase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mAlbumDatabase = Room.inMemoryDatabaseBuilder(context, AlbumDatabase.class).build();
    }

    @After
    public void closeDb() throws IOException {
        mAlbumDatabase.close();
    }

    @Test
    public void populateDatabaseThenGetAlbumList() throws Exception{

        AlbumDao albumDao = mAlbumDatabase.albumDao();

        List<Album> newAlbumList = TestUtil.createAlbumList(10);
        albumDao.insert(newAlbumList);

        LiveData<List<Album>> liveAlbums = albumDao.fetchAlbums();

        Assert.assertEquals("album list size ", 10, extractLiveDataValue(liveAlbums).size());




    }

    public static <T> T extractLiveDataValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        return (T) data[0];
    }



}
