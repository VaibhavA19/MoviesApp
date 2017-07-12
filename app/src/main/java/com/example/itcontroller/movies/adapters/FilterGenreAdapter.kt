package com.example.itcontroller.movies.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.Movie
import kotlinx.android.synthetic.main.list_item_genre.view.*

/**
 * Created by ITCONTROLLER on 7/11/2017.
 */
class FilterGenreAdapter(var genreList: Array<Movie.Genre>)
    : RecyclerView.Adapter<FilterGenreAdapter.GenreViewHolder>() {

    internal var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(oicl: OnItemClickListener) {
        this.onItemClickListener = oicl
    }

    fun updateGenreList(glist: Array<Movie.Genre>) {
        this.genreList = glist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GenreViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder?, position: Int) {
        holder?.bindView(genreList[position])
    }

    override fun getItemCount(): Int = genreList.size

    inner class GenreViewHolder(var GenreView: View)
        : RecyclerView.ViewHolder(GenreView) {
        fun bindView(thisGenre: Movie.Genre) {
            GenreView.rButton.text = thisGenre.name
            GenreView.rButton.setOnClickListener {
                Log.d("aaa", "in adapter1")
                onItemClickListener?.onItemClick(thisGenre.id, GenreView)
                Log.d("aaa", "in adapter2")
            }
            Log.d("aaa", "in adapter")
        }
    }
}