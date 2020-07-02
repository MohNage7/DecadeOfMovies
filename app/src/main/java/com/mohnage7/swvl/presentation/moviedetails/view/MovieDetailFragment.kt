package com.mohnage7.swvl.presentation.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohnage7.swvl.R
import com.mohnage7.swvl.presentation.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.item_movie_placeholder.*

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(movie)
    }

    private fun setViews(movie: Movie?) {
        movie?.let {
            titleTxtView.text = movie.title
            ratingTxtView.text = movie.rating
            yearTxtView.text = movie.year
            genresTxtView.text = movie.getGenresListAsString()
            castTxtView.text = movie.getCastListAsString()
        }
    }

    companion object {
        /**
         * The fragment argument representing the movie that this fragment
         * represents.
         */
        const val ARG_MOVIE = "movie"
    }
}