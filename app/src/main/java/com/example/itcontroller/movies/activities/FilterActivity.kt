package com.example.itcontroller.movies.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import com.example.itcontroller.movies.APIs.API
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.Utils.StringUtils
import com.example.itcontroller.movies.adapters.FilterGenreAdapter
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.GenreListResult
import com.example.itcontroller.movies.models.Movie
import kotlinx.android.synthetic.main.activity_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity() {

    var genreString :StringBuilder = StringBuilder()

    var resultIntent = Intent()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        filter_genre_rv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        var gAdapter: FilterGenreAdapter = FilterGenreAdapter(arrayOf<Movie.Genre>())
        gAdapter.setOnItemClickListener(OnItemClickListener { itemId, itemView ->
            genreString.append(itemId.toString()+",")
                Log.d("aaa","genre addaed "+genreString.toString())
            resultIntent.putExtra(StringUtils.GENRE_VALUE , genreString.toString())
        })
        filter_genre_rv.adapter = gAdapter
        API.getAPIinstance().genreListAPI.getGenreList().enqueue(object : Callback<GenreListResult?> {
            override fun onFailure(call: Call<GenreListResult?>?, t: Throwable?) {
                Log.d("aaa","gemre download failed")
            }

            override fun onResponse(call: Call<GenreListResult?>?, response: Response<GenreListResult?>?) {
                Log.d("aaa", response!!.body()!!.genres.size.toString())
                gAdapter.updateGenreList(response?.body()!!.genres)
            }
        })
        /*var resultIntent = Intent()
        resultIntent.putExtra("key","some data")
        setResult(Activity.RESULT_OK,resultIntent)*/

    }

    fun OnSortClick(view: View) {
        var checked: Boolean = (view as RadioButton).isChecked()
        Log.d("aaa", "on sort")
        when (view.id) {
            R.id.popularityRB
            -> {
                Log.d("aaa", "on sort  pop outside if")
                if (checked) {
                    resultIntent.putExtra(StringUtils.SORT_BY, StringUtils.POPULARITY_DESC)
                }
            }
            R.id.releaseRB
            -> {
                Log.d("aaa", "on sort  res outside if")
                if (checked) {
                    resultIntent.putExtra(StringUtils.SORT_BY, StringUtils.RELEASE_DESC)
                }
            }
        }
    }

    fun OnAdultClick(view: View) {
        var checked: Boolean = (view as RadioButton).isChecked()
        Log.d("aaa", "on adult click")
        when (view.id) {
            R.id.adultfalse
            -> {
                Log.d("aaa", "on adult  false outside if")
                if (checked) {
                    resultIntent.putExtra(StringUtils.INCLUDE_ADULT, "false")
                    Log.d("aaa", "adult false")
                }
            }
            R.id.adulttrue
            -> {
                if (checked) {
                    resultIntent.putExtra(StringUtils.INCLUDE_ADULT, "true")
                    Log.d("aaa", "adult true")
                }
                Log.d("aaa", "on adult true outside if")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
        when (itemId) {
            R.id.action_applyFilter -> {
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

