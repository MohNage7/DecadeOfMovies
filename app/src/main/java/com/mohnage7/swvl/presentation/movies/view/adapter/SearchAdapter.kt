package com.mohnage7.swvl.presentation.movies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohnage7.domain.SearchItem
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.base.BaseViewHolder
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.swvl.presentation.movies.view.callback.MovieClickListener
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_search.view.*

const val ITEM_MOVIE = 0
const val ITEM_CATEGORY = 1


class SearchAdapter(
    private val itemsList: List<SearchItem>,
    private val onMovieClickListener: MovieClickListener
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View
        return if (viewType == ITEM_MOVIE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
            MoviesViewHolder(view)
        } else {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            CategoryViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindViews(position)
    }

    override fun getItemCount() = itemsList.size


    override fun getItemViewType(position: Int) = when (itemsList[position]) {
        is SearchItem.ResultMovie -> ITEM_MOVIE
        is SearchItem.Category -> ITEM_CATEGORY
    }

    private inner class MoviesViewHolder internal constructor(itemView: View) :
        BaseViewHolder(itemView) {

        override fun bindViews(position: Int) {
            super.bindViews(position)
            val resultMovie = itemsList[position] as SearchItem.ResultMovie
            itemView.titleTxtView.text = resultMovie.title
            itemView.ratingTxtView.text = resultMovie.rating.toString()
            itemView.setOnClickListener {
                setOnItemClickListener(resultMovie)
            }
        }

        fun setOnItemClickListener(resultMovie: SearchItem.ResultMovie) {
            onMovieClickListener.onMovieClick(
                Movie(
                    resultMovie.title,
                    resultMovie.year,
                    resultMovie.rating,
                    resultMovie.genresList,
                    resultMovie.castList
                )
            )
        }

        override fun clear() {
            itemView.titleTxtView.text = ""
            itemView.ratingTxtView.text = ""
        }
    }

    private inner class CategoryViewHolder internal constructor(itemView: View) :
        BaseViewHolder(itemView) {

        override fun bindViews(position: Int) {
            super.bindViews(position)
            val movie = itemsList[position] as SearchItem.Category
            itemView.categoryTxtView.text = movie.year.toString()
        }


        override fun clear() {
            itemView.categoryTxtView.text = ""
        }
    }
}
