
package com.denizsubasi.moviesapp.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import com.denizsubasi.moviesapp.domain.model.MovieObject

/**
 * A Data Binding Component implementation for fragments.
 */
class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {

    override fun getCompanion1(): MovieObject.Companion {
        return MovieObject.Companion
    }

    override fun getCompanion2(): MovieObject.Credits.Cast.Companion {
        return MovieObject.Credits.Cast.Companion
    }

    private val adapter = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters() = adapter
}
