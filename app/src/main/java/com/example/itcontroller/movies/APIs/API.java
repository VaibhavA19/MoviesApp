package com.example.itcontroller.movies.APIs;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ITCONTROLLER on 7/7/2017.
 */

public class API {
    private static API APIinstance = null;

    private PopularMovieAPI popularMovieAPI;
    private NowPlayingAPI nowPlayingAPI;
    private TopRatedAPI topRatedAPI;
    private UpComingAPI upComingAPI;
    private ParticularMoveAPI particularMoveAPI;
    private SimilarMovieAPI similarMovieAPI;
    private DiscoverMoviesAPI discoverMoviesAPI;
    private GenreListAPI genreListAPI;

    private API() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        popularMovieAPI = retrofit.create(PopularMovieAPI.class);
        nowPlayingAPI = retrofit.create(NowPlayingAPI.class);
        topRatedAPI = retrofit.create(TopRatedAPI.class);
        upComingAPI = retrofit.create(UpComingAPI.class);
        particularMoveAPI = retrofit.create(ParticularMoveAPI.class);
        similarMovieAPI = retrofit.create(SimilarMovieAPI.class);
        discoverMoviesAPI = retrofit.create(DiscoverMoviesAPI.class);
        genreListAPI = retrofit.create(GenreListAPI.class);
    }

    public static API getAPIinstance() {
        if (APIinstance == null) {
            Log.d("aaa", "in api get instance");
            APIinstance = new API();
        }

        return APIinstance;
    }


    public PopularMovieAPI getPopularMovieAPI() {
        return popularMovieAPI;
    }

    public NowPlayingAPI getNowPlayingAPI() {
        return nowPlayingAPI;
    }

    public TopRatedAPI getTopRatedAPI() {
        return topRatedAPI;
    }

    public UpComingAPI getUpComingAPI() {
        return upComingAPI;
    }

    public ParticularMoveAPI getParticularMoveAPI() {
        return particularMoveAPI;
    }

    public SimilarMovieAPI getSimilarMovieAPI() {
        return similarMovieAPI;
    }

    public DiscoverMoviesAPI getDiscoverMoviesAPI() {
        return discoverMoviesAPI;
    }

    public GenreListAPI getGenreListAPI() {
        return genreListAPI;
    }
}
