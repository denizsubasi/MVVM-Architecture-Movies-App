package com.denizsubasi.moviesapp.binding.activity

import android.app.Activity
import androidx.databinding.DataBindingComponent
import com.denizsubasi.moviesapp.domain.model.MovieObject

class ActivityDataBindingComponent (activity: Activity) : DataBindingComponent {

    override fun getCompanion2(): MovieObject.Credits.Cast.Companion {
        return MovieObject.Credits.Cast.Companion
    }

    override fun getCompanion1(): MovieObject.Companion {
        return MovieObject.Companion
    }


    override fun getFragmentBindingAdapters() = null
}
