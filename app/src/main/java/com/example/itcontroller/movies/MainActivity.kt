package com.example.itcontroller.movies

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.itcontroller.movies.Utils.StringUtils
import com.example.itcontroller.movies.activities.DiscoverMoviesActivity
import com.example.itcontroller.movies.activities.MoviesViewActivity
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("aaa", "Main Activity")
        val i :Intent = Intent(this@MainActivity,MoviesViewActivity::class.java)
        var di  = Intent(this@MainActivity,DiscoverMoviesActivity::class.java)
        btnNowPlaying.setOnClickListener {
            i.putExtra(StringUtils.MOVIE_VIEW_TYPE, StringUtils.NOW_PLAYING)
            startActivity(i)
        }
        btnPopular.setOnClickListener {
            i.putExtra(StringUtils.MOVIE_VIEW_TYPE, StringUtils.POPULAR)
            startActivity(i)
        }
        btnTopRated.setOnClickListener {
            i.putExtra(StringUtils.MOVIE_VIEW_TYPE, StringUtils.TOP_RATED)
            startActivity(i)
        }
        btnUpcoming.setOnClickListener {
            i.putExtra(StringUtils.MOVIE_VIEW_TYPE, StringUtils.UP_COMING)
            startActivity(i)
        }
        btnDiscover.setOnClickListener {
            startActivity(di)
        }
    }
}
