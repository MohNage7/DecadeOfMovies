package com.mohnage7.decadeofmovies.presentation.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohnage7.decadeofmovies.R
import com.mohnage7.decadeofmovies.framework.base.BaseViewHolder
import com.mohnage7.decadeofmovies.presentation.model.Movie
import com.mohnage7.decadeofmovies.presentation.movies.view.callback.MovieClickListener
import kotlinx.android.synthetic.main.item_movie.view.*


class MoviesAdapter(
    private val movieList: List<Movie>,
    private val movieClickListener: MovieClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card, parent, false)
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
            itemView.titleTxtView.text = movie.title
            itemView.ratingTxtView.text = movie.rating.toString()
            itemView.yearTxtView.text = movie.year.toString()
            itemView.genresTxtView.text = movie.getGenresListAsString()
            itemView.setOnClickListener { movieClickListener.onMovieClick(movie) }
        }

        override fun clear() {
            itemView.titleTxtView.text = ""
            itemView.genresTxtView.text = ""
            itemView.titleTxtView.text = ""
            itemView.yearTxtView.text = ""
        }
    }
}
