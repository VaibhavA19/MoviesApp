package com.example.itcontroller.movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.itcontroller.movies.APIs.ImageBaseUrl
import com.example.itcontroller.movies.R
import com.example.itcontroller.movies.interfaces.OnItemClickListener
import com.example.itcontroller.movies.models.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_similar_movie.*
import kotlinx.android.synthetic.main.list_item_similar_movie.view.*

/**
 * Created by ITCONTROLLER on 7/8/2017.
 */
class SimilarMoviesAdapter(var results:Array<Movie> , val sim_pb:ProgressBar): RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieViewHolder>() {

    internal var onItemClickListener:OnItemClickListener? = null
    fun setOnItemClickListener(oicl:OnItemClickListener){
        this.onItemClickListener = oicl
    }
    override fun onBindViewHolder(holder: SimilarMovieViewHolder?, position: Int) {
        holder!!.bindView(results[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimilarMovieViewHolder {
        var view:View = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item_similar_movie,parent,false)
        return SimilarMovieViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    fun update(list:Array<Movie>){
        this.results  = list
        sim_pb.visibility = View.INVISIBLE
        notifyDataSetChanged()
    }
    inner class SimilarMovieViewHolder(var SimilarMovieView: View):RecyclerView.ViewHolder(SimilarMovieView){
        fun bindView( movie :Movie){
            SimilarMovieView.setOnClickListener {
                onItemClickListener?.onItemClick(movie.id,SimilarMovieView)
            }
            SimilarMovieView.similar_movie_title.text = movie.title
            var url :String = ImageBaseUrl.noImageUrl
            if(movie.poster_path != null){
                url = ImageBaseUrl.baseUrl + movie.poster_path
            }
            Picasso.with(SimilarMovieView.context)
                    .load(url)
                    .centerCrop()
                    .resize(100,100)
                    .into(SimilarMovieView.similar_movie_poster,
                            object: Callback {
                                override fun onSuccess() {
                                    SimilarMovieView.progressBarSimilarMovie.visibility = View.INVISIBLE
                                }
                                override fun onError() {}
                            }
                            )
        }
    }
}