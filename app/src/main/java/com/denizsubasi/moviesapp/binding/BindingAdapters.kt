
package com.denizsubasi.moviesapp.binding

import androidx.databinding.BindingAdapter
import android.view.View

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}
