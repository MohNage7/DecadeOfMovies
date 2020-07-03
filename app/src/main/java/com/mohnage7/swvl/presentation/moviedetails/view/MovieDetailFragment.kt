package com.mohnage7.swvl.presentation.moviedetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.mohnage7.network.PhotosRequestConfig
import com.mohnage7.swvl.R
import com.mohnage7.swvl.presentation.model.DataWrapper
import com.mohnage7.swvl.presentation.model.Movie
import com.mohnage7.swvl.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.item_movie_placeholder.*
import kotlinx.android.synthetic.main.layout_images_viewpager.*
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
        setViews(movie)
        movie?.title?.let {
            movieDetailsViewModel.getMoviePhotos(
                PhotosRequestConfig(
                    "f204401bb9bb1d699f145f55ed61df03", movieName = it
                )
            )

            movieDetailsViewModel.observePostsChanges()
                .observe(this, androidx.lifecycle.Observer { dataWrapper ->
                    when (dataWrapper.status) {
                        DataWrapper.Status.SUCCESS -> {
                            dataWrapper.data?.let { photosList -> setupViewpager(photosList) }
                        }
                    }
                })

        }

//        setupViewpager(
//            listOf(
//                "https://farm66.static.flickr.com/65535/49961792951_35c3344c0f.jpg",
//                "https://farm66.static.flickr.com/65535/32885403967_be14b95a9c.jpg",
//                "https://farm66.static.flickr.com/65535/48105179031_b5aeb65eb0.jpg"
//            )
//        )
    }


    private fun setupViewpager(comicList: List<String>) {
        val comicsViewPager = ImagesViewPager(comicList)
        viewPager.adapter = comicsViewPager
        // You need to retain one page on each side so that the next and previous items are visible
        viewPager.offscreenPageLimit = 1
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx =
            resources.getDimension(R.dimen.viewpager_next_item_visible).toInt()
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt()
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer =
            ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                // Next line scales the item's height. You can remove it if you don't want this effect
                page.scaleY = 1 - 0.25f * abs(position)
            }
        viewPager.setPageTransformer(pageTransformer)
        viewPager.addItemDecoration(
            HorizontalMarginItemDecoration(
                activity!!,
                R.dimen.viewpager_current_item_horizontal_margin
            )
        )
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
                    comicList.size
                )
            }
        })
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