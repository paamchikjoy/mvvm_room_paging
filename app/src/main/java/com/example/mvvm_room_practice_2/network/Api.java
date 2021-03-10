package com.example.mvvm_room_practice_2.network;

import com.example.mvvm_room_practice_2.model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("movies.php")
    Call<List<Movies>> getAllMovies();






}
