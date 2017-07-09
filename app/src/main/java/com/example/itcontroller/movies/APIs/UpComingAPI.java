package com.example.itcontroller.movies.APIs;

import com.example.itcontroller.movies.models.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ITCONTROLLER on 7/7/2017.
 */

public interface UpComingAPI {
    @GET("/3/movie/upcoming?api_key=ffaab051f63b8088437b3fae0a2b2f68&language=en-US&page=1")
    Call<MovieResult> getUpComing();
}
