package com.denizsubasi.moviesapp.ui.flow.common

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.databinding.ViewHolderMovieBinding
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.util.ApplicationConstants.Companion.IMAGE_URL
import com.denizsubasi.moviesapp.vo.AppExecutors

/**
 * A RecyclerView headerAdapter for [com.sample.moviesapp.domain.model.MovieObject
 *] class.
 */
class MovieListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClick: ((MovieObject) -> Unit)?
) : DataBoundListAdapter<MovieObject, ViewHolderMovieBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<MovieObject>() {
            override fun areItemsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
                return oldItem.title == newItem.title
            }
        }
) {

    override fun createBinding(parent: ViewGroup): ViewHolderMovieBinding {
        val binding = DataBindingUtil.inflate<ViewHolderMovieBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_holder_movie,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.movie?.let {
                itemClick?.invoke(it)
            }
        }
        binding.imageUrl = IMAGE_URL + binding.movie?.posterPath
        return binding
    }

    override fun bind(binding: ViewHolderMovieBinding, item: MovieObject) {
        binding.movie = item
        binding.moreItemsView.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
        binding.imageUrl = IMAGE_URL + binding.movie?.posterPath

        val popularity = binding.movie?.voteAverage ?: 0.0
        if (popularity != 0.0 && popularity > 1.0) {
            binding.rateBg.visibility = View.VISIBLE
            val customSpannableString = com.denizsubasi.moviesapp.util.CustomSpannableString("")
            customSpannableString.append(popularity.toString().split(".").first(), StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.5f))
            customSpannableString.append(".")
            customSpannableString.append(popularity.toString().split(".").getOrNull(1)
                    ?: "", StyleSpan(Typeface.NORMAL))
            binding.movieRate.text = customSpannableString.toString()
        } else {
            binding.rateBg.visibility = View.GONE
        }
    }
}
