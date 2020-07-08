package com.mohnage7.decadeofmovies.presentation.moviedetails.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mohnage7.decadeofmovies.R
import com.mohnage7.decadeofmovies.framework.extentions.addFragment
import com.mohnage7.decadeofmovies.presentation.model.Movie
import com.mohnage7.decadeofmovies.presentation.movies.view.MoviesActivity

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MoviesActivity].
 */
class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = MovieDetailFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(
                            MovieDetailFragment.ARG_MOVIE,
                            intent.getParcelableExtra<Movie>(MovieDetailFragment.ARG_MOVIE)
                        )
                    }
                }
            addFragment(fragment, R.id.item_detail_container, false)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    override fun onBackPressed() {
        super.onBackPressed()
        // exits activity with image transaction animation
        overridePendingTransition(R.anim.no_animation, R.anim.anim_slide_down)
    }
}