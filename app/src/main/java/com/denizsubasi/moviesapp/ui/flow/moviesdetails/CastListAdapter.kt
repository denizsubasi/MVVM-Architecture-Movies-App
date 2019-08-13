package com.denizsubasi.moviesapp.ui.flow.moviesdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.databinding.ViewHolderCastBinding
import com.denizsubasi.moviesapp.domain.model.MovieObject.Credits.Cast
import com.denizsubasi.moviesapp.ui.flow.common.DataBoundListAdapter
import com.denizsubasi.moviesapp.vo.AppExecutors

/**
 * A RecyclerView headerAdapter for [com.sample.moviesapp.domain.model.Cast
 *] class.
 */
class CastListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClick: ((Cast) -> Unit)?
) : DataBoundListAdapter<Cast, ViewHolderCastBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.castId == newItem.castId
            }
        }
) {

    override fun createBinding(parent: ViewGroup): ViewHolderCastBinding {
        val binding = DataBindingUtil.inflate<ViewHolderCastBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_holder_cast,
                parent,
                false,
                dataBindingComponent
        )
        binding.cardView.setOnClickListener {
            binding.cast?.let {
                itemClick?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ViewHolderCastBinding, item: Cast) {
        binding.cast = item
    }
}
