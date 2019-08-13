package com.denizsubasi.moviesapp.ui.flow.moviesdetails

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
import com.denizsubasi.moviesapp.databinding.ViewHolderSimilarVideoBinding
import com.denizsubasi.moviesapp.domain.model.MovieObject.SimilarVideos.SimilarVideoObject
import com.denizsubasi.moviesapp.ui.flow.common.DataBoundListAdapter
import com.denizsubasi.moviesapp.util.ApplicationConstants
import com.denizsubasi.moviesapp.vo.AppExecutors

/**
 * A RecyclerView headerAdapter for [com.sample.moviesapp.domain.model.SimilarVideoObject
 *] class.
 */
class SimilarMoviesAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClick: ((SimilarVideoObject) -> Unit)?
) : DataBoundListAdapter<SimilarVideoObject, ViewHolderSimilarVideoBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<SimilarVideoObject>() {
            override fun areItemsTheSame(oldItem: SimilarVideoObject, newItem: SimilarVideoObject): Boolean {
                return oldItem.similarVideoId == newItem.similarVideoId
            }

            override fun areContentsTheSame(oldItem: SimilarVideoObject, newItem: SimilarVideoObject): Boolean {
                return oldItem.originalTitle == newItem.originalTitle
            }
        }
) {

    override fun createBinding(parent: ViewGroup): ViewHolderSimilarVideoBinding {
        val binding = DataBindingUtil.inflate<ViewHolderSimilarVideoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_holder_similar_video,
                parent,
                false,
                dataBindingComponent
        )
        binding.cardView.setOnClickListener {
            binding.movie?.let {
                itemClick?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ViewHolderSimilarVideoBinding, item: SimilarVideoObject) {
        binding.movie = item
        if (binding.movie?.similarVideoId ?: 0 == -101) {
            binding.contentLayout.visibility = View.GONE
            binding.moreItemsView.visibility = View.VISIBLE
        } else {
            binding.moreItemsView.visibility = View.GONE
            binding.contentLayout.visibility = View.VISIBLE
            binding.imageUrl = ApplicationConstants.IMAGE_URL + binding.movie?.posterPath
        }
        val popularity = binding.movie?.voteAverage ?: 0.0
        if (popularity != 0.0 && popularity > 1.0) {
            binding.rateBg.visibility = View.VISIBLE
            val customSpannableString = com.denizsubasi.moviesapp.util.CustomSpannableString("")
            customSpannableString.append(popularity.toString().split(".").first(), StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.5f))
            customSpannableString.append(".")
            customSpannableString.append(popularity.toString().split(".").getOrNull(1)
                    ?: "", StyleSpan(Typeface.NORMAL))
            binding.movieRate.text = customSpannableString.toString()
        }else{
            binding.rateBg.visibility = View.GONE
        }
    }
}
