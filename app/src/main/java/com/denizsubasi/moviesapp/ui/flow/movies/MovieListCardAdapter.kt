package com.denizsubasi.moviesapp.ui.flow.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.databinding.ViewHolderCardMovieBinding
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.ui.flow.common.DataBoundListAdapter
import com.denizsubasi.moviesapp.util.ApplicationConstants.Companion.IMAGE_URL
import com.denizsubasi.moviesapp.vo.AppExecutors

class MovieListCardAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemCLickCallback: ((MovieObject) -> Unit)?
) : DataBoundListAdapter<MovieObject, ViewHolderCardMovieBinding>
(appExecutors, object : DiffUtil.ItemCallback<MovieObject>() {
    override fun areItemsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
        return oldItem.title == newItem.title
    }

}) {
    override fun createBinding(parent: ViewGroup): ViewHolderCardMovieBinding {
        val binding = DataBindingUtil.inflate<ViewHolderCardMovieBinding>(
                LayoutInflater.from(parent.context), R.layout.view_holder_card_movie, parent, false, dataBindingComponent)
        binding.root.setOnClickListener {
            itemCLickCallback?.let {
                binding?.movie?.let { movie ->
                    it(movie)

                }
            }
        }
        return binding
    }

    override fun bind(binding: ViewHolderCardMovieBinding, item: MovieObject) {
        binding.movie = item
        binding.moreItemsView.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
        binding.imageUrl = IMAGE_URL + binding.movie?.posterPath

    }
}