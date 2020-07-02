package com.mohnage7.swvl.presentation.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.base.BaseViewHolder
import com.mohnage7.swvl.presentation.movies.model.Movie
import com.mohnage7.swvl.presentation.movies.view.callback.MovieClickListener
import kotlinx.android.synthetic.main.item_movie.view.*


class MoviesAdapter(
    private val movieList: List<Movie>,
    private val movieClickListener: MovieClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindViews(position)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private inner class MoviesViewHolder internal constructor(itemView: View) :
        BaseViewHolder(itemView) {

        override fun bindViews(position: Int) {
            val movie = movieList[position]
            itemView.movieTitleTv.text = movie.title
            itemView.genresTv.text = movie.getGenresAsString()
            itemView.movieTitleTv.text = movie.title
            itemView.yearTv.text = movie.year
            itemView.setOnClickListener { movieClickListener.onMovieClick(movie) }
        }

        override fun clear() {
            itemView.movieTitleTv.text = ""
            itemView.genresTv.text = ""
            itemView.movieTitleTv.text = ""
            itemView.yearTv.text = ""
        }
    }
}
