package com.example.itcontroller.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.itcontroller.movies.APIs.ImageBaseUrl
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.VoteColorSelect
import com.example.itcontroller.movies.models.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

/**
 * Created by ITCONTROLLER on 7/7/2017.
 */
class MovieAdapter(var movieArray: Array<Movie>, var mpb: ProgressBar) : RecyclerView.Adapter<MovieAdapter.MovieViewHolderr>() {
    override fun onBindViewHolder(holder: MovieViewHolderr?, position: Int) {
        holder?.bindView(movieArray[position])
    }

    override fun getItemCount(): Int = movieArray.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolderr {
        var itemView: View = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolderr(itemView)
    }

    internal var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener): Unit {
        this.onItemClickListener = onItemClickListener
    }
    fun updateMovieList(mArray:Array<Movie>){
        this.movieArray = mArray
        mpb.visibility = View.INVISIBLE
        notifyDataSetChanged()
    }

    inner class MovieViewHolderr(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(movie: Movie): Unit {

            itemView.setOnClickListener { onItemClickListener?.onItemClick(movie.id, itemView) }

            itemView.movie_tvTitle.text = movie.title
            itemView.movie_tvLanguage.text = movie.original_language
            itemView.movie_voteColor.setBackgroundColor(VoteColorSelect.getColor(movie.vote_average))
            itemView.movie_tvVotes.text = movie.vote_average.toString() + " Votes"
            var url: String
            if (movie.poster_path != null) {
                url = ImageBaseUrl.baseUrl + movie.poster_path
            } else {
                url = ImageBaseUrl.noImageUrl
            }
            Picasso.with(itemView.context).load(url).resize(100, 120).centerCrop().into(itemView.movie_ImvPoster, object : Callback {
                override fun onSuccess() {
                    itemView.poster_progressBar.visibility = View.INVISIBLE
                }

                override fun onError() {}
            });
        }
    }
}