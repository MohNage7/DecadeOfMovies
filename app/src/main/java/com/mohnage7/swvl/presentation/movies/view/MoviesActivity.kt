package com.mohnage7.swvl.presentation.movies.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.mohnage7.swvl.R
import com.mohnage7.swvl.presentation.extentions.replaceFragment
import com.mohnage7.swvl.presentation.moviedetails.view.MovieDetailFragment
import com.mohnage7.swvl.presentation.moviedetails.view.MovieDetailsActivity
import com.mohnage7.swvl.presentation.movies.model.Movie
import com.mohnage7.swvl.presentation.movies.view.callback.MovieClickListener

class MoviesActivity : AppCompatActivity(), MovieClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
    }

    override fun onMovieClick(movie: Movie) {
        if (twoPane) {
            navigateToDetailFragmentWith(movie)
        } else {
            launchDetailActivityWith(movie)
        }
    }

    private fun launchDetailActivityWith(movie: Movie) {
        val bundle = Bundle().apply {
            putParcelable(MovieDetailFragment.ARG_MOVIE, movie)
        }
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    private fun navigateToDetailFragmentWith(movie: Movie) {
        val fragment = MovieDetailFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(MovieDetailFragment.ARG_MOVIE, movie)
                }
            }
        replaceFragment(fragment, R.id.item_detail_container, false)
    }
}