package com.example.itcontroller.movies.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.itcontroller.movies.APIs.API
import com.example.itcontroller.movies.APIs.ImageBaseUrl
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.adapters.SimilarMoviesAdapter
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.Movie
import com.example.itcontroller.movies.models.MovieResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_particualr_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticualrMovieActivity : AppCompatActivity() {

    var isSeeMoreClicked: Boolean = false
    var downloadedParticularMovie: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particualr_movie)
        val movieId: Int = intent.getIntExtra("movieId", -1)
        Log.d("pma", movieId.toString() + "")
        API.getAPIinstance().particularMoveAPI.getMovieById(movieId).enqueue(object : Callback<Movie?> {
            override fun onFailure(call: Call<Movie?>?, t: Throwable?) {
                Log.d("aaa", "Particualr movie download failed")
            }

            override fun onResponse(call: Call<Movie?>?, response: Response<Movie?>?) {
                Log.d("pma", "on response of particular movie-----" + response?.body()!!)
                downloadedParticularMovie = response!!.body()!!
                bindView(downloadedParticularMovie!!)
            }
        })
        Log.d("pma", "similar movie donload start")
        ///////////////////////////////////Similar Movies/////////////////////////////////////
        particular_movie_rvSimilar_movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var similarMovieAdapter: SimilarMoviesAdapter = SimilarMoviesAdapter(arrayOf<Movie>(), similar_movie_pb)
        Log.d("pma", "similar movie after creating adapter")
        similarMovieAdapter.onItemClickListener = OnItemClickListener { itemId, itemView ->
            val i: Intent = Intent(this@ParticualrMovieActivity, ParticualrMovieActivity::class.java)
            i.putExtra("movieId", itemId)
            startActivity(i)
        }
        particular_movie_rvSimilar_movies.adapter = similarMovieAdapter
        Log.d("pma", "similar movie set adapter")
        API.getAPIinstance().similarMovieAPI.getMoviesSimilarToMovieId(movieId).enqueue(object : Callback<MovieResult?> {
            override fun onFailure(call: Call<MovieResult?>?, t: Throwable?) {}

            override fun onResponse(call: Call<MovieResult?>?, response: Response<MovieResult?>?) {
                Log.d("pma", "similar movie donload response")
                similarMovieAdapter.update(response!!.body()!!.results)
                Log.d("pma", "similar movie after adapter update  ----" + response!!.body()!!.results.size)

                Log.d("pma", "similar movie donload on response")
            }
        })
        /////////////////////////////////////////////////////////////////////////////////////////
        expandView.setOnClickListener {
            if (!isSeeMoreClicked) {
                particular_movieSeemore.text = "See less"
                particular_movie_tvOverview.maxLines = Int.MAX_VALUE
                isSeeMoreClicked = true
            } else {
                particular_movieSeemore.text = "See more"
                isSeeMoreClicked = false
                particular_movie_tvOverview.maxLines = 2
            }
        }
    }


    fun bindView(thisMovie: Movie): Unit {
        Log.d("pma", "bind view starts")
        particular_movie_tvOverview.text = thisMovie.overview
        particular_movie_tvRelease.text = thisMovie.release_date
        particular_tvMovieRuntime.text = thisMovie.runtime.toString() + " min"
        particular_movie_tvTitle.text = thisMovie.title
        particular_movie_tvVoteCount.text = thisMovie.vote_count.toString() + " votes"
        particular_tvMoviePopularity.text = thisMovie.popularity.toInt().toString()
        Log.d("pma", "bind view 1")
        setPoster(thisMovie.poster_path)
        Log.d("pma", "bind view 2 ")
        setGenre(thisMovie.genres)
        Log.d("pma", "bind view 3 ")
        setSpokenLanguages(thisMovie.spoken_languages)
        Log.d("pma", "bind view")
    }

    fun setGenre(gList: Array<Movie.Genre>) {
        val listSize: Int = gList.size - 1

        for (i in 0..listSize) {
            particular_movie_tvGenre.append(gList[i].name)
            if (i != listSize) {
                particular_movie_tvGenre.append(" ,")
            }
        }
    }

    fun setSpokenLanguages(langList: Array<Movie.SpokenLanguages>) {
        val listSize: Int = langList.size - 1
        for (i in 0..listSize) {
            particular_movie_tvspokenLangs.append(langList[i].name)
            if (i != listSize) {
                particular_movie_tvspokenLangs.append(" ,")
            }
        }
    }

    fun setPoster(path: String?) {
        var url: String = ImageBaseUrl.noImageUrl
        if (path != null) {
            url = ImageBaseUrl.baseUrl + path
        }
        Picasso.with(this).load(url).centerCrop().resize(170, 190).into(particular_moviePoster, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                progressBar.visibility = View.INVISIBLE
            }

            override fun onError() {
            }
        })
    }
}
