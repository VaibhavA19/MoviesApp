package com.example.itcontroller.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.itcontroller.movies.APIs.API;
import com.example.itcontroller.movies.models.Movie;
import com.example.itcontroller.movies.models.MovieResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("aaa", "Main Activity");
        API.getAPIinstance().getNowPlayingAPI().getNowPlaying().enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {


                /////////////////////////////////////////////////////////////////////////////////////////////////
                Log.d("aaa", response.body().getResults().length + "");
                for (int i = 0; i < response.body().getResults().length; i++) {
                    Log.d("aaa", "" + response.body().getResults()[i].getAdult());
                    Log.d("aaa", "" + response.body().getResults()[i].getOriginal_language());
                    Log.d("aaa", "" + response.body().getResults()[i].getOriginal_title());
                    Log.d("aaa", "" + response.body().getResults()[i].getPopularity());
                    Log.d("aaa", "=========================================================");
                }
                ///////////////////////////////////////////////////////////////////////////////////////////////////
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d("aaa", "failed");
            }
        });
    }
}
