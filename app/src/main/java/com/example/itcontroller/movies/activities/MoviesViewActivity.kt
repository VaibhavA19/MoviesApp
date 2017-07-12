package com.example.itcontroller.movies.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.itcontroller.movies.APIs.API
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.Utils.StringUtils
import com.example.itcontroller.movies.adapters.MovieAdapter
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.Movie
import com.example.itcontroller.movies.models.MovieResult
import kotlinx.android.synthetic.main.activity_movies_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_view)

        movie_pb.visibility = View.VISIBLE

        movies_view_rv.layoutManager = LinearLayoutManager(this)
        val movieAdapter: MovieAdapter = MovieAdapter(arrayOf<Movie>(), movie_pb)
        movieAdapter.setOnItemClickListener(OnItemClickListener { itemId, itemView ->
            val i: Intent = Intent(this@MoviesViewActivity, ParticualrMovieActivity::class.java)
            i.putExtra("movieId", itemId)
            startActivity(i)
        })
        movies_view_rv.adapter = movieAdapter

        var MOVIE_TYPE: String = intent.getStringExtra(StringUtils.MOVIE_VIEW_TYPE)

        val cBObject: Callback<MovieResult?> = object : Callback<MovieResult?> {
            override fun onResponse(call: Call<MovieResult?>?, response: Response<MovieResult?>?) {
                movieAdapter.updateMovieList(response!!.body()!!.results)
            }

            override fun onFailure(call: Call<MovieResult?>?, t: Throwable?) {
                movie_pb.visibility = View.INVISIBLE
                Toast.makeText(this@MoviesViewActivity, "Download Failed", Toast.LENGTH_LONG).show()
                Log.d("aaa", "Download Failed")
            }
        }

        when (MOVIE_TYPE) {
            StringUtils.NOW_PLAYING -> {
                setTitle(StringUtils.NOW_PLAYING)
                API.getAPIinstance().nowPlayingAPI.nowPlaying.enqueue(cBObject)
            }
            StringUtils.POPULAR -> {
                setTitle(StringUtils.POPULAR)
                API.getAPIinstance().popularMovieAPI.popularMovies.enqueue(cBObject)
            }
            StringUtils.TOP_RATED -> {
                setTitle(StringUtils.TOP_RATED)
                API.getAPIinstance().topRatedAPI.topRated.enqueue(cBObject)
            }
            StringUtils.UP_COMING -> {
                setTitle(StringUtils.UP_COMING)
                API.getAPIinstance().upComingAPI.upComing.enqueue(cBObject)
            }
        }
    }
}
