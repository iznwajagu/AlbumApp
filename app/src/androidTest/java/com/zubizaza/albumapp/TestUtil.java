package com.zubizaza.albumapp;

import com.zubizaza.albumapp.data.model.Album;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Album> createAlbumList(int listSize){
        List<Album> albumList = new ArrayList<>();

        for(int i = 0 ; i < listSize ; i++){
            albumList.add(new Album("user" + i, "" + i, "title" + 1));
        }

        return albumList;
    }


}
