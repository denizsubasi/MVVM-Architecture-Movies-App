package com.denizsubasi.moviesapp.ui.flow.moviesdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.binding.activity.ActivityDataBindingComponent
import com.denizsubasi.moviesapp.data.mapper.convertSimilarVideoToMovieObject
import com.denizsubasi.moviesapp.databinding.ActivityMovieDetailsBinding
import com.denizsubasi.moviesapp.di.ViewModelFactory
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.ui.flow.common.RetryCallback
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.playerflow.VideoPlayerActivity
import com.denizsubasi.moviesapp.util.ApplicationConstants
import com.denizsubasi.moviesapp.vo.AppExecutors
import com.denizsubasi.moviesapp.vo.Status
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_appbar.back
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesDetailsActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MovieDetailsViewModel
    @Inject
    lateinit var appExecutors: AppExecutors
    var dataBindingComponent: DataBindingComponent = ActivityDataBindingComponent(this)
    lateinit var binding: ActivityMovieDetailsBinding
    lateinit var castListAdapter: CastListAdapter
    lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private val SHOW_RELEASE_DATE_FORMATTER = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
    private val RELEASE_DATE_FORMATTER = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
    var movieId = -1
    private var movieObject: MovieObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details
                , dataBindingComponent)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
        binding.response = viewModel.movieDetailsLive
        setContentView(binding.root)
        intent.extras?.let {
            movieObject = it.getParcelable(ApplicationConstants.MOVIE_ITEM)
            movieObject?.let {
                movieId = it.id
                viewModel.fetchMovieDetails(it.id)
                setSomeLayoutInformations(it)
            } ?: run {
                movieId = it.getInt(ApplicationConstants.MOVIE_ID, -1)
                if (movieId != -1) {
                    viewModel.fetchMovieDetails(movieId = movieId)
                }
            }
        }
        setAdapters()
        setClickListeners()
        setSubscriptions()

    }

    private fun setSomeLayoutInformations(movieObject: MovieObject) {
        binding.ratingBar.rating = movieObject.voteAverage.toFloat() / 2f
        binding.isNotEmptyVoteAverage = movieObject.isNotEmptyVoteAverage()
        if (!movieObject.releaseDate.isNullOrBlank()) {
            binding.releaseDate.text = getString(R.string.releaseDate, SHOW_RELEASE_DATE_FORMATTER.format(RELEASE_DATE_FORMATTER.parse(movieObject.releaseDate)))
        }
    }

    private fun setAdapters() {
        val rvCrewAdapter = CastListAdapter(dataBindingComponent, appExecutors) {

        }
        castListAdapter = rvCrewAdapter
        crewRecyclerView.adapter = castListAdapter

        val rvSimilarMoviesAdapter = SimilarMoviesAdapter(dataBindingComponent, appExecutors) { similarVideo ->
            val intent = Intent(applicationContext, MoviesDetailsActivity::class.java)
            intent.putExtra(ApplicationConstants.MOVIE_ITEM, convertSimilarVideoToMovieObject(similarVideo))
            startActivity(intent)
        }
        similarMoviesAdapter = rvSimilarMoviesAdapter
        similarMoviesRecyclerView.adapter = similarMoviesAdapter
    }

    private fun setSubscriptions() {
        viewModel.movieDetailsLive.observe(this, Observer {
            if (it.status == Status.SUCCESS) {
                binding.movie = it.data
                movieObject = it.data
                playTrailer.visibility = if (movieObject?.videos?.videoList?.isNotEmpty() == true) View.VISIBLE else View.GONE
                it.data?.credits?.cast?.let {
                    if (it.isNotEmpty()) {
                        val list = it.filter { it.profilePath?.isNotBlank() ?: false }
                        if (list.isNotEmpty()) {
                            fullCastTextview.visibility = View.VISIBLE
                            castListAdapter.submitList(list)
                        }
                    }
                }
                it.data?.similar?.similarVideoList?.let {
                    if (it.isNotEmpty()) {
                        similarVideosTextview.visibility = View.VISIBLE
                        similarMoviesAdapter.submitList(it)
                    }
                }
                binding.runtime.text = calculateRunTime()
            }
        })
    }

    private fun calculateRunTime(): String {
        var formattedRuntime = ""
        val runtime = movieObject?.runtime ?: 0
        val hour = TimeUnit.MINUTES.toHours(runtime.toLong())
        var min = TimeUnit.MINUTES.toMinutes(runtime.toLong())
        if (hour != 0L) {
            formattedRuntime += getString(R.string.hour, hour)
            min -= (hour * 60)
        }
        if (min != 0L) {
            formattedRuntime += " ${getString(R.string.min, min)}"
        }
        return formattedRuntime
    }

    private fun setClickListeners() {
        back.setOnClickListener {
            onBackPressed()
        }
        playTrailer.setOnClickListener {
            startVideoPlayer(movieObject)
        }

        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                if (movieId != -1) {
                    viewModel.fetchMovieDetails(movieId = movieId)
                }
            }
        }
    }

    private fun startVideoPlayer(data: MovieObject?) {
        val intent = Intent(applicationContext, VideoPlayerActivity::class.java)
        intent.putExtra(ApplicationConstants.MOVIE_ITEM, data)
        startActivity(intent)

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector


}