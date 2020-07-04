package com.mohnage7.swvl.presentation.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.mohnage7.network.model.PhotosRequestConfig
import com.mohnage7.swvl.BuildConfig.API_KEY
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.extentions.makeGone
import com.mohnage7.swvl.framework.extentions.makeVisible
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.swvl.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.item_movie_placeholder.*
import kotlinx.android.synthetic.main.layout_images_viewpager.*
import kotlinx.android.synthetic.main.layout_loading_photos.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.abs

/**
 * A fragment representing a single movie detail screen.
 * This fragment is either contained in a [MovieDetailActivity]
 * in two-pane mode (on tablets) or a [MoviesActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    private var movie: Movie? = null
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

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
        movie?.let { movie ->
            setViews(movie)
            requestMoviePhotos(movie)
            observeMoviePhotosListChanges()

        }
    }

    private fun observeMoviePhotosListChanges() {
        movieDetailsViewModel.observePostsChanges()
            .observe(this, androidx.lifecycle.Observer { dataWrapper ->
                handleResult(dataWrapper)
            })
    }

    private fun handleResult(dataWrapper: DataWrapper<List<String>>) {
        when (dataWrapper.status) {
            DataWrapper.Status.SUCCESS -> {
                hideLoading()
                if (dataWrapper.data.isNullOrEmpty()) {
                    hideContent()
                } else {
                    showContent()
                    setupViewpager(dataWrapper.data)
                }
            }
            DataWrapper.Status.ERROR -> {
                hideLoading()
                hideContent()
                handleError(dataWrapper.message)
            }
            DataWrapper.Status.LOADING -> showLoading()
        }
    }

    private fun requestMoviePhotos(
        movie: Movie
    ) {
        movie.title?.let {
            movieDetailsViewModel.getMoviePhotos(
                PhotosRequestConfig(
                    API_KEY,
                    movieName = it,
                    movieId = movie.getMovieUniqueId()
                )
            )
        }
    }

    private fun showContent() {
        viewPager.makeVisible()
        countTxtView.makeVisible()
    }

    private fun hideContent() {
        viewPager.makeGone()
        countTxtView.makeGone()
    }

    private fun handleError(message: String?) {
        message?.let { Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
    }

    private fun showLoading() {
        shimmerFrameLayout.makeVisible()
    }

    private fun hideLoading() {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.makeGone()
    }


    private fun setupViewpager(photosList: List<String>?) {
        photosList?.let { list ->
            val comicsViewPager = ImagesViewPager(list)
            viewPager.adapter = comicsViewPager
            // You need to retain one page on each side so that the next and previous items are visible
            viewPager.offscreenPageLimit = 1
            // set page animation
            val pageTransformer = getViewPagerTransformation()
            viewPager.setPageTransformer(pageTransformer)
            // show parts of the side images
            viewPager.addItemDecoration(
                HorizontalMarginItemDecoration(
                    activity!!,
                    R.dimen.viewpager_current_item_horizontal_margin
                )
            )
            setOnPageChangeListener(list)
        }
    }

    private fun setOnPageChangeListener(photosList: List<String>) {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                countTxtView.text = String.format(
                    Locale.ENGLISH,
                    "%d/%d",
                    position + 1,
                    photosList.size
                )
            }
        })
    }

    private fun getViewPagerTransformation(): ViewPager2.PageTransformer {
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx =
            resources.getDimension(R.dimen.viewpager_next_item_visible).toInt()
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt()
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        return ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - 0.25f * abs(position)
        }
    }

    private fun setViews(movie: Movie?) {
        movie?.let {
            titleTxtView.text = movie.title
            ratingTxtView.text = movie.rating.toString()
            yearTxtView.text = movie.year.toString()
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