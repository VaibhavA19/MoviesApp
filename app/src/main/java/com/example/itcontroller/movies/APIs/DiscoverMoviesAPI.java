package com.example.itcontroller.movies.APIs;

import com.example.itcontroller.movies.models.MovieResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ITCONTROLLER on 7/10/2017.
 */

public interface DiscoverMoviesAPI {
    @GET("/3/discover/movie?api_key=ffaab051f63b8088437b3fae0a2b2f68&language=en-US&sort_by=release_date.asc&page=1")
    Call<MovieResult> getDiscoverableMovies();

    @GET("/3/discover/movie")
    Call<MovieResult> getSortAdultGenre(
            @QueryMap(encoded = true) Map<String, String> qmap
    );
}
