package com.mohnage7.swvl.presentation.moviedetails.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mohnage7.swvl.R
import com.mohnage7.swvl.presentation.movies.model.Movie

/**
 * A fragment representing a single movie detail screen.
 * This fragment is either contained in a [MovieDetailActivity]
 * in two-pane mode (on tablets) or a [MoviesActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_MOVIE)) {
                movie = it.getParcelable(ARG_MOVIE)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_detail, container, false)
    }

    companion object {
        /**
         * The fragment argument representing the movie that this fragment
         * represents.
         */
        const val ARG_MOVIE = "movie"
    }
}