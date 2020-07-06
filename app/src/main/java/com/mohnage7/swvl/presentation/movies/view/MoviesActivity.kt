package com.mohnage7.swvl.presentation.movies.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohnage7.domain.SearchItem
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.extentions.makeGone
import com.mohnage7.swvl.framework.extentions.makeVisible
import com.mohnage7.swvl.framework.extentions.observe
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
        setSupportActionBar(toolbar)
        // observe data changes
        with(moviesViewModel) {
            observe(observeSearchMoviesList(), ::renderSearchData)
            observe(observeMoviesList(), ::renderMoviesData)
        }
    }


    private fun renderSearchData(dataWrapper: DataWrapper<List<SearchItem>>) {
        when (dataWrapper.status) {
            Status.LOADING -> showSearchLoading()
            Status.ERROR -> {
                hideSearchLoading()
                setSearchViewsVisibility(false)
                handleSearchError(dataWrapper.message)
            }
            Status.SUCCESS -> {
                hideSearchLoading()
                if (!dataWrapper.data.isNullOrEmpty()) {
                    setSearchViewsVisibility(true)
                    setupSearchAdapter(dataWrapper.data)
                } else {
                    setSearchViewsVisibility(false)
                }
            }
        }
    }

    private fun renderMoviesData(dataWrapper: DataWrapper<List<Movie>>) {
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
        overridePendingTransition(R.anim.anim_slide_up, R.anim.no_animation)
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
        menuInflater.inflate(R.menu.main, menu)
        // init
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        val searchEditText = searchView.findViewById<EditText>(R.id.search_src_text)
        // set hint and its color
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.grey))
        searchView.queryHint = getString(R.string.hint_search)
        // set listeners
        setQueryTextListenerFor(searchView)
        setOnActionExpandListenerFor(menuItem, searchView)
        return true
    }

    private fun setQueryTextListenerFor(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                searchInMovies(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setOnActionExpandListenerFor(
        menuItem: MenuItem,
        searchView: SearchView
    ) {
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
    }

    private fun showSearchLoading() {
        searchLayout.makeVisible()
        progressBar.makeVisible()
        noSearchDataTxtView.makeGone()
        searchRecyclerView.makeGone()
    }

    private fun hideSearchLoading() {
        progressBar.makeGone()
    }

    private fun handleSearchError(message: String?) {
        noSearchDataTxtView.text =
            if (message == null || message.isEmpty()) getString(R.string.no_data_found) else message
    }

    private fun setSearchViewsVisibility(dataAvailable: Boolean) {
        searchLayout.makeVisible()
        if (dataAvailable) {
            showSearchContent()
        } else {
            showNoSearchDataLayout()
        }
    }

    private fun setupSearchAdapter(moviesList: List<SearchItem>) {
        val adapter = SearchAdapter(moviesList, this)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = adapter
    }

    private fun showNoSearchDataLayout() {
        searchRecyclerView.makeGone()
        noSearchDataTxtView.makeVisible()
    }

    private fun showSearchContent() {
        searchRecyclerView.makeVisible()
        noSearchDataTxtView.makeGone()
    }

    private fun searchInMovies(query: String) {
        moviesViewModel.updateSearchQuery(query)
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
}