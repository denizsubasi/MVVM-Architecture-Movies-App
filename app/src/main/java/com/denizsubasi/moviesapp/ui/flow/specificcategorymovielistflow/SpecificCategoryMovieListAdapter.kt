package com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.databinding.ViewHolderCardMovieMiddleBinding
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.ui.flow.common.DataBoundListAdapter
import com.denizsubasi.moviesapp.vo.AppExecutors

class SpecificCategoryMovieListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemCLickCallback: ((MovieObject) -> Unit)?
) : DataBoundListAdapter<MovieObject, ViewHolderCardMovieMiddleBinding>
(appExecutors, object : DiffUtil.ItemCallback<MovieObject>() {
    override fun areItemsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
        return oldItem.title == newItem.title
    }

}) {
    override fun createBinding(parent: ViewGroup): ViewHolderCardMovieMiddleBinding {
        val binding = DataBindingUtil.inflate<ViewHolderCardMovieMiddleBinding>(
                LayoutInflater.from(parent.context), R.layout.view_holder_card_movie_middle, parent, false, dataBindingComponent)
        binding.root.setOnClickListener {
            binding.movie?.let { movie ->
                itemCLickCallback?.let {
                    it(movie)
                }
            }
        }
        return binding
    }

    override fun bind(binding: ViewHolderCardMovieMiddleBinding, item: MovieObject) {
        binding.movie = item
    }
}