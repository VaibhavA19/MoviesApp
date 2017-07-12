package com.example.itcontroller.movies.APIs

import com.example.itcontroller.movies.models.GenreListResult
import com.example.itcontroller.movies.models.Movie
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by ITCONTROLLER on 7/10/2017.
 */
interface GenreListAPI {
    @GET("/3/genre/movie/list?api_key=ffaab051f63b8088437b3fae0a2b2f68&language=en-US")
    fun getGenreList(): Call<GenreListResult>
}