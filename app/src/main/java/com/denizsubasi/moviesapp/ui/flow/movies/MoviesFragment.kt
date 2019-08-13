package com.denizsubasi.moviesapp.ui.flow.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.databinding.FragmentMoviesBinding
import com.denizsubasi.moviesapp.binding.FragmentDataBindingComponent
import com.denizsubasi.moviesapp.di.Injectable
import com.denizsubasi.moviesapp.di.ViewModelFactory
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.ui.flow.common.MovieListAdapter
import com.denizsubasi.moviesapp.ui.flow.common.RetryCallback
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.MoviesDetailsActivity
import com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow.SpecificCategoryMoviesActivity
import com.denizsubasi.moviesapp.util.ApplicationConstants.Companion.MOVIE_ITEM
import com.denizsubasi.moviesapp.util.autoCleared
import com.denizsubasi.moviesapp.vo.AppExecutors
import com.denizsubasi.moviesapp.vo.Status
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModel: MoviesViewModel

    lateinit var mostPopularMoviesCardAdapter: MovieListAdapter
    lateinit var mostPopularMoviesCardVerticalAdapter: MovieListCardAdapter
    lateinit var nowPlayingMoviesCardAdapter: MovieListAdapter
    lateinit var upComingMoviesCardAdapter: MovieListAdapter
    private var noUpComingMovies = false

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentMoviesBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false, dataBindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.response = viewModel.mostPopularMoviesResultLive
        setSubscriptions()
        setAdapters()
        setClickListeners()
        viewModel.fetchMovies()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun setClickListeners() {
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.fetchMovies()
            }
        }
    }


    private fun startSpecificCategoryMoviesActivity(data: MoviesResponseItem?) {
        val intent = Intent(context, SpecificCategoryMoviesActivity::class.java)
        intent.putExtra(MOVIE_ITEM, data)
        startActivity(intent)

    }

    private fun startMovieDetailsActivity(data: MovieObject?) {
        val intent = Intent(context, MoviesDetailsActivity::class.java)
        intent.putExtra(MOVIE_ITEM, data)
        startActivity(intent)

    }

    private fun setAdapters() {

        val rvMostPopularMoviesAdapter = MovieListAdapter(dataBindingComponent, appExecutors) { clickedItem ->
            if (clickedItem.id == -101) {
                startSpecificCategoryMoviesActivity(viewModel.mostPopularMoviesResultLive.value?.data)
            } else {
                startMovieDetailsActivity(clickedItem)
            }
        }
        mostPopularMoviesCardAdapter = rvMostPopularMoviesAdapter

        val rvMostPopularVerticalAdapter = MovieListCardAdapter(dataBindingComponent, appExecutors) { clickedItem ->
            if (clickedItem.id == -101) {
                startSpecificCategoryMoviesActivity(viewModel.mostPopularMoviesResultLive.value?.data)
            } else {
                startMovieDetailsActivity(clickedItem)
            }
        }
        mostPopularMoviesCardVerticalAdapter = rvMostPopularVerticalAdapter

        val rvUpcomingMoviesAdapter = MovieListAdapter(dataBindingComponent, appExecutors) { clickedItem ->
            if (clickedItem.id == -101) {
                startSpecificCategoryMoviesActivity(viewModel.upcomingMoviesResultLive.value?.data)
            } else {
                startMovieDetailsActivity(clickedItem)
            }
        }
        upComingMoviesCardAdapter = rvUpcomingMoviesAdapter

        val rvNowPlayingMoviesAdapter = MovieListAdapter(dataBindingComponent, appExecutors) { clickedItem ->
            if (clickedItem.id == -101) {
                startSpecificCategoryMoviesActivity(viewModel.nowPlayingMoviesResultLive.value?.data)
            } else {
                startMovieDetailsActivity(clickedItem)
            }
        }
        nowPlayingMoviesCardAdapter = rvNowPlayingMoviesAdapter

        binding.upcomingMoviesRecyclerView.adapter = upComingMoviesCardAdapter
        binding.nowPlayingMoviesRecyclerView.adapter = nowPlayingMoviesCardAdapter

    }

    private fun initMostPopularRecyclerView() {
        val llm = LinearLayoutManager(context)
        if (noUpComingMovies) {
            llm.orientation = RecyclerView.VERTICAL
            binding.mostPopularMoviesRecyclerView.layoutManager = llm
            binding.mostPopularMoviesRecyclerView.adapter = mostPopularMoviesCardVerticalAdapter
        } else {
            llm.orientation = RecyclerView.HORIZONTAL
            binding.mostPopularMoviesRecyclerView.layoutManager = llm
            binding.mostPopularMoviesRecyclerView.adapter = mostPopularMoviesCardAdapter
        }
    }

    private fun setSubscriptions() {

        viewModel.mostPopularMoviesResultLive.observe(viewLifecycleOwner, Observer { result ->
            val moviesList = result?.data?.movieObject ?: arrayListOf()
            if (moviesList.isNotEmpty()) {
                if (moviesList.size > 5) {
                    textPopular.visibility = View.VISIBLE
                }
            }
            if (result.status != Status.LOADING) {
                if (noUpComingMovies) {
                    mostPopularMoviesCardVerticalAdapter.submitList(result?.data?.movieObject)
                } else {
                    mostPopularMoviesCardAdapter.submitList(result?.data?.movieObject)
                }
                initMostPopularRecyclerView()
            }
        })

        viewModel.nowPlayingMoviesResultLive.observe(this, Observer { result ->
            val moviesList = result?.data?.movieObject ?: arrayListOf()
            if (moviesList.isNotEmpty()) {
                nowPlayingText.visibility = View.VISIBLE
                nowPlayingMoviesCardAdapter.submitList(result?.data?.movieObject)
            }
        })

        viewModel.upcomingMoviesResultLive.observe(this, Observer { result ->
            val moviesList = result?.data?.movieObject ?: arrayListOf()
            if (moviesList.isNotEmpty()) {
                noUpComingMovies = moviesList.size < 5
                if (!noUpComingMovies) {
                    upComingMoviesCardAdapter.submitList(result?.data?.movieObject)
                    upcomingText.visibility = View.VISIBLE
                }
            } else {
                noUpComingMovies = true
            }
        })

    }

}