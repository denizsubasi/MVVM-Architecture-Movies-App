package com.denizsubasi.moviesapp.domain.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["responseType"])
data class MoviesResponseItem(

        @ColumnInfo(name = "page")
        @field:SerializedName("page")
        var page: Int = 0,

        @field:SerializedName("results")
        @ColumnInfo(name = "movieObject")
        var movieObject: ArrayList<MovieObject> = arrayListOf(),

        @field:SerializedName("total_pages")
        @ColumnInfo(name = "totalPages")
        var totalPages: Int = 0,

        @field:SerializedName("total_results")
        @ColumnInfo(name = "totalResults")
        var totalResults: Int = 0,

        @field:SerializedName("responseType")
        @ColumnInfo(name = "responseType")
        var responseType: Int = 0

) : Parcelable {
    enum class MovieResponseTypes(val value: Int) {
        MOST_POPULAR(0), NOW_PLAYING(1), UPCOMING(2)
    }
}