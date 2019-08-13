package com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.binding.activity.ActivityDataBindingComponent
import com.denizsubasi.moviesapp.databinding.ActivitySpecificCategoryFilmListBinding
import com.denizsubasi.moviesapp.di.ViewModelFactory
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.ui.flow.common.RetryCallback
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.MoviesDetailsActivity
import com.denizsubasi.moviesapp.util.ApplicationConstants.Companion.MOVIE_ITEM
import com.denizsubasi.moviesapp.util.PaginationScrollListener
import com.denizsubasi.moviesapp.vo.AppExecutors
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_specific_category_film_list.*
import kotlinx.android.synthetic.main.layout_appbar.*
import javax.inject.Inject


class SpecificCategoryMoviesActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var viewModel: SpecificCategoryMoviesViewModel
    lateinit var binding: ActivitySpecificCategoryFilmListBinding
    var dataBindingComponent: DataBindingComponent = ActivityDataBindingComponent(this)
    private var items = ArrayList<MovieObject>()
    private var isRecyclerLoading = false
    private var currentPage = 1
    private var movieType: Int = 0
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: SpecificCategoryMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_specific_category_film_list)
        setContentView(binding.root)
        back.visibility = View.VISIBLE
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SpecificCategoryMoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.response = viewModel.moviesLive
        setClickListeners()
        setAdapters()
        addRecyclerPositionListener()
        setSubscriptions()
        intent.extras?.let {
            val movieObject: MoviesResponseItem? = it.getParcelable(MOVIE_ITEM)
            movieObject?.let {
                movieType = it.responseType
                loadMovies()
            }
        }


    }

    private fun startMovieDetailsActivity(data: MovieObject?) {
        val intent = Intent(applicationContext, MoviesDetailsActivity::class.java)
        intent.putExtra(MOVIE_ITEM, data)
        startActivity(intent)

    }

    private fun addRecyclerPositionListener() {
        moviesRecyclerView.addOnScrollListener(object :
                PaginationScrollListener(gridLayoutManager) {
            override var isLoading: Boolean
                get() = isRecyclerLoading
                set(value) {
                    isRecyclerLoading = value

                }

            override fun loadMoreItems() {
                isLoading = true
                loadMovies()
            }
        })
    }

    private fun loadMovies() {
        viewModel.loadMovies(movieType, currentPage)
    }

    private fun setAdapters() {
        gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        val itemDecoration = com.denizsubasi.moviesapp.util.ItemOffsetDecoration(this, R.dimen.item_offset)
        binding.moviesRecyclerView.addItemDecoration(itemDecoration)
        // binding.moviesRecyclerView.setHasFixedSize(true)
        val rvAdapter = SpecificCategoryMovieListAdapter(dataBindingComponent, appExecutors) {
            startMovieDetailsActivity(it)
        }
        adapter = rvAdapter
        binding.moviesRecyclerView.layoutManager = gridLayoutManager
        binding.moviesRecyclerView.adapter = adapter
    }

    private fun setSubscriptions() {
        viewModel.moviesLive.observe(this, Observer {
            it.data?.let {
                if (it.page == 1) {
                    items.clear()
                }
                items.addAll(it.movieObject)
                adapter.submitList(items)
                isRecyclerLoading = false
                currentPage += 1
            }

        })
    }

    private fun setClickListeners() {
        back.setOnClickListener {
            onBackPressed()
        }
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                loadMovies()
            }
        }
    }

    override fun onStop() {
        super.onStop()

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

}