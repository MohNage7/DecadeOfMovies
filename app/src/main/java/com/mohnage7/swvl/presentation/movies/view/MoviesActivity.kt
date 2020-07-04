package com.mohnage7.swvl.presentation.movies.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohnage7.domain.SearchItem
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.extentions.makeGone
import com.mohnage7.swvl.framework.extentions.makeVisible
import com.mohnage7.swvl.framework.extentions.replaceFragment
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.swvl.presentation.model.DataWrapper.Status
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.swvl.presentation.moviedetails.view.MovieDetailFragment
import com.mohnage7.swvl.presentation.moviedetails.view.MovieDetailsActivity
import com.mohnage7.swvl.presentation.movies.view.adapter.MoviesAdapter
import com.mohnage7.swvl.presentation.movies.view.adapter.SearchAdapter
import com.mohnage7.swvl.presentation.movies.view.callback.MovieClickListener
import com.mohnage7.swvl.presentation.movies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.layout_loading_movies.*
import kotlinx.android.synthetic.main.layout_movies_list.*
import kotlinx.android.synthetic.main.layout_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity(), MovieClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private val twoPane: Boolean by lazy { isLargeDevice() }
    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        observeMoviesList()

        setSupportActionBar(toolbar)

        moviesViewModel.observeSearchMoviesList().observe(this, Observer { dataWrapper ->
            when (dataWrapper.status) {
                Status.LOADING -> showSearchLoading()
                Status.ERROR -> handleSearchError(dataWrapper.message)
                Status.SUCCESS -> {
                    dataWrapper?.data?.let { setupSearchAdapter(it) }

                }
            }
        })
    }

    /**
     * The detail container view will be present only in the
     * large-screen layouts (res/values-w900dp).
     * If this view is present, then the
     * activity should be in two-pane mode.
     */
    private fun isLargeDevice(): Boolean {
        return findViewById<NestedScrollView>(R.id.item_detail_container) != null
    }

    private fun observeMoviesList() {
        moviesViewModel
            .observeMoviesList()
            .observe(this, Observer { dataWrapper ->
                handleResult(dataWrapper)
            })
    }

    private fun handleResult(dataWrapper: DataWrapper<List<Movie>>) {
        when (dataWrapper.status) {
            Status.SUCCESS -> {
                hideLoading()
                setMoviesAdapter(dataWrapper.data)
            }
            Status.LOADING -> showLoading()
        }
    }

    private fun setMoviesAdapter(data: List<Movie>?) {
        data?.let {
            moviesRecyclerView.adapter = MoviesAdapter(it, this)
        }
    }


    private fun showLoading() {
        shimmerFrameLayout.makeVisible()
    }

    private fun hideLoading() {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.makeGone()
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchEditText = searchView.findViewById<EditText>(R.id.search_src_text)
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.soft_grey))
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.queryHint = getString(R.string.hint_search)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.trim { it <= ' ' }.length > 1) {
                    searchView.clearFocus()
                    searchInMovies(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                searchLayout.visibility = View.GONE
                searchView.onActionViewExpanded()
                return true
            }
        })
        return true
    }

    private fun showSearchLoading() {
        searchLayout.makeVisible()
        progressBar.makeVisible()
        noSearchDataTxtView.makeGone()
        searchRecyclerView.makeGone()
    }

    private fun handleSearchError(message: String?) {
        setSearchViewsVisibility(false)
        noSearchDataTxtView.text =
            if (message == null || message.isEmpty()) getString(R.string.no_data_found) else message
    }

    private fun setSearchViewsVisibility(dataAvailable: Boolean) {
        searchLayout.makeVisible()
        progressBar.makeGone()
        if (dataAvailable) {
            searchRecyclerView.makeVisible()
            noSearchDataTxtView.makeGone()
        } else {
            searchRecyclerView.makeGone()
            noSearchDataTxtView.makeVisible()
        }
    }

    private fun setupSearchAdapter(moviesList: List<SearchItem>) {
        setSearchViewsVisibility(true)
        val adapter = SearchAdapter(moviesList, this)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = adapter
    }

    private fun searchInMovies(query: String) {
        moviesViewModel.updateSearchQuery(query)
    }

}