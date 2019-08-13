package com.denizsubasi.moviesapp.domain.model


import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.denizsubasi.moviesapp.util.ApplicationConstants
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class MovieObject(
        @SerializedName("adult")
        var adult: Boolean = false,
        @SerializedName("backdrop_path")
        var backdropPath: String? = "",
        @SerializedName("budget")
        var budget: Int = 0,
        @SerializedName("credits")
        @Embedded
        var credits: Credits = Credits(),
        @SerializedName("genres")
        var genres: ArrayList<Genre> = arrayListOf<Genre>(),
        @SerializedName("homepage")
        var homepage: String? = "",
        @SerializedName("id")
        @PrimaryKey
        var id: Int = 0,
        @SerializedName("images")
        @Embedded
        var images: Images = Images(),
        @SerializedName("imdb_id")
        var imdbId: String? = "",
        @SerializedName("original_language")
        var originalLanguage: String? = "",
        @SerializedName("original_title")
        var originalTitle: String? = "",
        @SerializedName("overview")
        var overview: String? = "",
        @SerializedName("popularity")
        var popularity: Double = 0.0,
        @SerializedName("poster_path")
        var posterPath: String? = "",
        @SerializedName("production_companies")
        var productionCompanies: ArrayList<ProductionCompany> = arrayListOf<ProductionCompany>(),
        @SerializedName("production_countries")
        var productionCountries: ArrayList<ProductionCountry> = arrayListOf<ProductionCountry>(),
        @SerializedName("release_date")
        var releaseDate: String? = "",
        @SerializedName("reviews")
        @Embedded
        var reviews: Reviews = Reviews(),
        @SerializedName("runtime")
        var runtime: Int = 0,
        @SerializedName("similar")
        @Embedded
        var similar: SimilarVideos = SimilarVideos(),
        @SerializedName("spoken_languages")
        var spokenLanguages: ArrayList<SpokenLanguage> = arrayListOf<SpokenLanguage>(),
        @SerializedName("status")
        var status: String? = "",
        @SerializedName("tagline")
        var tagline: String? = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("video")
        var video: Boolean = false,
        @SerializedName("videos")
        @Embedded
        var videos: Videos = Videos(),
        @SerializedName("vote_average")
        var voteAverage: Double = 0.0,
        @SerializedName("vote_count")
        var voteCount: Int = 0
) : Parcelable {

    companion object {
        @BindingAdapter("movieImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view.context).load(imageUrl).into(view)
            }
        }

    }

    fun voteAverageString(): String = voteAverage.toString()

    fun isNotEmptyVoteAverage(): Boolean = voteAverage != 0.0

    fun movieImageUrl(): String {
        return ApplicationConstants.IMAGE_URL + posterPath
    }

    fun isOverviewEmpty() = overview?.isEmpty() ?: false

    fun getGenresText(): String {
        var genresText = ""
        genres.forEachIndexed { index, text ->
            genresText += "${text.name}" + if (index + 1 == genres.size) "" else ","
        }
        return genresText
    }

    @Parcelize
    @Entity
    data class Genre(
            @SerializedName("id")
            @PrimaryKey
            var GenreId: Int = 0,
            @SerializedName("name")
            var name: String = ""
    ) : Parcelable

    @Parcelize
    @Entity
    data class ProductionCompany(
            @SerializedName("id")
            @PrimaryKey(autoGenerate = true)
            var productionCompanyId: Int = 0,
            @SerializedName("logo_path")
            var logoPath: String? = "",
            @SerializedName("name")
            var name: String? = "",
            @SerializedName("origin_country")
            var originCountry: String? = ""
    ) : Parcelable

    @Parcelize
    @Entity
    data class Reviews(
            @PrimaryKey(autoGenerate = true) val reviewsId: Int = 0,
            @SerializedName("page")
            var reviewPage: Int = 0,
            @SerializedName("results")
            var reviewList: ArrayList<Review> = arrayListOf(),
            @SerializedName("total_pages")
            var reviewsTotalPages: Int = 0
    ) : Parcelable {
        @Parcelize
        @Entity
        data class Review(
                @SerializedName("author")
                var author: String? = "",
                @SerializedName("content")
                var content: String? = "",
                @SerializedName("id")
                @PrimaryKey
                var reviewId: String? = "",
                @SerializedName("url")
                var url: String? = ""
        ) : Parcelable
    }

    @Parcelize
    @Entity
    data class Credits(
            @PrimaryKey(autoGenerate = true) val creditsId: Int = 0,
            @SerializedName("cast")
            var cast: ArrayList<Cast> = arrayListOf(),
            @SerializedName("crew")
            var crew: ArrayList<Crew> = arrayListOf()
    ) : Parcelable {

        @Parcelize
        @Entity
        data class Crew(
                @SerializedName("credit_id")
                var creditId: String? = "",
                @SerializedName("department")
                var department: String? = "",
                @SerializedName("gender")
                var gender: Int = 0,
                @SerializedName("id")
                @PrimaryKey
                var crewId: Int = 0,
                @SerializedName("job")
                var job: String? = "",
                @SerializedName("name")
                var name: String? = "",
                @SerializedName("profile_path")
                var profilePath: String? = ""
        ) : Parcelable {

        }

        @Parcelize
        @Entity
        data class Cast(
                @SerializedName("cast_id")
                var castId: Int = 0,
                @SerializedName("character")
                var character: String? = "",
                @SerializedName("credit_id")
                var creditId: String? = "",
                @SerializedName("gender")
                var gender: Int = 0,
                @SerializedName("id")
                @PrimaryKey
                var id: Int = 0,
                @SerializedName("name")
                var name: String? = "",
                @SerializedName("order")
                var order: Int = 0,
                @SerializedName("profile_path")
                var profilePath: String? = ""
        ) : Parcelable {
            companion object {
                @BindingAdapter("castProfilePath")
                fun loadImage(view: ImageView, imageUrl: String?) {
                    imageUrl?.let {
                        Glide.with(view.context).load(imageUrl).into(view)
                    }
                }
            }

            fun castProfilePath(): String {
                return ApplicationConstants.IMAGE_URL + profilePath
            }
        }
    }

    @Parcelize
    @Entity
    data class ProductionCountry(
            @SerializedName("iso_3166_1")
            var iso31661: String? = "",
            @SerializedName("name")
            var name: String? = "",
            @PrimaryKey(autoGenerate = true) val productionCountryId: Int
    ) : Parcelable

    @Parcelize
    @Entity
    data class Images(
            @SerializedName("backdrops")
            var backdrops: ArrayList<String> = arrayListOf(),
            @SerializedName("posters")
            var posters: ArrayList<String> = arrayListOf(),
            @PrimaryKey(autoGenerate = true) val imagesId: Int = 0
    ) : Parcelable

    @Parcelize
    @Entity
    data class Videos(
            @SerializedName("results")
            var videoList: ArrayList<VideoObject> = arrayListOf(),
            @PrimaryKey(autoGenerate = true) val videosId: Int = 0
    ) : Parcelable {
        @Parcelize
        @Entity
        data class VideoObject(
                @SerializedName("id")
                @PrimaryKey
                var videoId: String? = "",
                @SerializedName("iso_3166_1")
                var iso31661: String? = "",
                @SerializedName("iso_639_1")
                var iso6391: String? = "",
                @SerializedName("key")
                var key: String? = "",
                @SerializedName("name")
                var name: String? = "",
                @SerializedName("site")
                var site: String? = "",
                @SerializedName("size")
                var size: Int = 0,
                @SerializedName("type")
                var type: String? = ""
        ) : Parcelable
    }

    @Parcelize
    @Entity
    data class SpokenLanguage(
            @PrimaryKey(autoGenerate = true) val spokensId: Int,
            @SerializedName("iso_639_1")
            var iso6391: String = "",
            @SerializedName("name")
            var name: String = ""
    ) : Parcelable

    @Parcelize
    @Entity
    data class SimilarVideos(
            @PrimaryKey(autoGenerate = true) val similarVideos: Int = 0,
            @SerializedName("page")
            var similarVideosPage: Int = 0,
            @SerializedName("results")
            var similarVideoList: ArrayList<SimilarVideoObject> = arrayListOf(),
            @SerializedName("total_pages")
            var similatVideosTotalPages: Int = 0
    ) : Parcelable {
        @Parcelize
        @Entity
        data class SimilarVideoObject(
                @SerializedName("adult")
                var adult: Boolean = false,
                @SerializedName("backdrop_path")
                var backdropPath: String? = "",
                @SerializedName("genre_ids")
                var genreIds: ArrayList<Int> = arrayListOf(),
                @SerializedName("id")
                @PrimaryKey
                var similarVideoId: Int = 0,
                @SerializedName("original_language")
                var originalLanguage: String? = "",
                @SerializedName("original_title")
                var originalTitle: String? = "",
                @SerializedName("overview")
                var overview: String = "",
                @SerializedName("popularity")
                var popularity: Double = 0.0,
                @SerializedName("poster_path")
                var posterPath: String? = "",
                @SerializedName("release_date")
                var releaseDate: String? = "",
                @SerializedName("title")
                var title: String? = "",
                @SerializedName("video")
                var video: Boolean = false,
                @SerializedName("vote_average")
                var voteAverage: Double = 0.0,
                @SerializedName("vote_count")
                var voteCount: Int = 0
        ) : Parcelable{
            fun isNotEmptyVoteAverage(): Boolean = voteAverage != 0.0

            fun movieImageUrl(): String {
                return ApplicationConstants.IMAGE_URL + posterPath
            }

        }
    }
}