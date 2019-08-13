package com.denizsubasi.moviesapp.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import javax.inject.Inject

class ImageLoader @Inject constructor(
        private val requestManager: RequestManager
) {

    fun loadImage(url: String, imageView: ImageView) {
        requestManager
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    fun loadImage(
            url: String,
            imageView: ImageView,
            listener: RequestListener
    ) {
        requestManager
                .load(url)
                .listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        listener.onLoadFailed()
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        listener.onResourceReady()
                        return false
                    }
                })
                .into(imageView)
    }

    data class RequestListener(
            val onResourceReady: (() -> Unit) = {},
            val onLoadFailed: (() -> Unit) = {}
    )
}


