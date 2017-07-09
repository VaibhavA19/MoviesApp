package com.example.itcontroller.movies.APIs;

import com.example.itcontroller.movies.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ITCONTROLLER on 7/8/2017.
 */

public interface ParticularMoveAPI {
    @GET("/3/movie/{movie_id}?api_key=ffaab051f63b8088437b3fae0a2b2f68&language=en-US")
    Call<Movie> getMovieById(@Path("movie_id") int movieId);
}
