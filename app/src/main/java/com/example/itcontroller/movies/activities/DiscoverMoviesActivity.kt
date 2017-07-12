package com.example.itcontroller.movies.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.itcontroller.movies.APIs.API
import com.example.itcontroller.movies.APIs.APIkey
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.Utils.StringUtils
import com.example.itcontroller.movies.adapters.MovieAdapter
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.Movie
import com.example.itcontroller.movies.models.MovieResult
import kotlinx.android.synthetic.main.activity_discover_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverMoviesActivity : AppCompatActivity() {


    var movieAdapter: MovieAdapter? = null
    var cbObject: Callback<MovieResult>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        movieAdapter = MovieAdapter(arrayOf<Movie>(), discover_movie_pb)
        movieAdapter?.setOnItemClickListener(OnItemClickListener { itemId, itemView ->
            var i = Intent(this@DiscoverMoviesActivity, ParticualrMovieActivity::class.java)
            i.putExtra("movieId", itemId)
            startActivity(i)
        })
        cbObject = object : Callback<MovieResult> {
            override fun onResponse(call: Call<MovieResult>?, response: Response<MovieResult>?) {
                discover_movies_view_rv.adapter = movieAdapter
                Log.d("aaa", "on response of discoveer moview" + response!!.body()!!.results.size)
                movieAdapter?.updateMovieList(response!!.body()!!.results)
            }

            override fun onFailure(call: Call<MovieResult>?, t: Throwable?) {
                Log.d("aaa", "discoverable download failed")

            }
        }
        addFilter.setOnClickListener {
            var i = Intent(this@DiscoverMoviesActivity, FilterActivity::class.java)
            startActivityForResult(i, REQUEST_FILTER)
        }
        discover_movies_view_rv.layoutManager = LinearLayoutManager(this)
        Log.d("aaa", "discoverable activity")
        API.getAPIinstance().discoverMoviesAPI.discoverableMovies.enqueue(cbObject)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("aaa", "onActivityResult")
        if (requestCode == REQUEST_FILTER) {
            if (resultCode == Activity.RESULT_OK) {
                ////////////////////////////////IncludeAdult
                Log.d("aaa", "onActivityResult inside if ")
                var includeAdult = data?.getStringExtra(StringUtils.INCLUDE_ADULT)
                if (includeAdult == null) {
                    includeAdult = "false"
                }
                //////////////////////////////////////////////////////
                ////////////////////////////////////SortBy
                Log.d("aaa", "onActivityResult inside if " + includeAdult)
                var sort = data?.getStringExtra(StringUtils.SORT_BY)
                if (sort == null) {
                    sort = StringUtils.POPULARITY_DESC
                }
                Log.d("aaa", "onActivityResult inside if " + sort)
                /////////////////////////////////////////////////////////
                ////////////////////////////////////////////////Genre
                var genre: String? = data?.getStringExtra(StringUtils.GENRE_VALUE)
                if (genre != null) {
                    genre = genre?.substring(0, genre.lastIndex)
                } else {
                    genre = ""
                }
                Log.d("aaa", "genre received " + genre)
                ////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////Adding data for queryMap
                var dataToSend: MutableMap<String, String> = HashMap<String, String>()
                dataToSend.put("api_key", APIkey.key)
                dataToSend.put("language", "en-US")
                dataToSend.put("sort_by", sort)
                dataToSend.put("include_adult", includeAdult)
                dataToSend.put("page", "1")
                dataToSend.put("with_genres", genre)
                //////////////////////////////////////////////////////////////////////////////////////////
                discover_movie_pb.visibility = View.VISIBLE
                API.getAPIinstance().discoverMoviesAPI.getSortAdultGenre(dataToSend)
                        .enqueue(cbObject)

            } else {
                discover_movie_pb.visibility = View.INVISIBLE
                Toast.makeText(this, "No filters added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
        when (itemId) {
            android.R.id.home -> {
                    NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    internal companion object {
        val REQUEST_FILTER = 123
    }
}
