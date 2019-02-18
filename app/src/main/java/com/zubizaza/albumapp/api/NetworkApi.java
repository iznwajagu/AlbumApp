package com.zubizaza.albumapp.api;

import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkApi {

    @GET("/albums")
    Call<List<Album>> fetchAlbums();

}
