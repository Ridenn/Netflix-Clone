package com.example.netflixclone.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (

    @SerializedName("movie_cover")
    val movieCover: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("cast")
    val cast: String?,

    @SerializedName("movie_player_cover")
    val moviePlayerCover: String?,

    @SerializedName("movie_video")
    val movieVideo: String?

) : Parcelable {
    companion object{
        const val EXTRA_KEY = "current_movie"
    }
}

class MoviesBuilder{
    var movieCover: String = ""
    var name: String = ""
    var description: String = ""
    var cast: String = ""
    var moviePlayerCover: String = ""
    var movieVideo: String = ""

    fun build(): Movie = Movie(movieCover, name, description, cast, moviePlayerCover, movieVideo)
}

fun movies(block: MoviesBuilder.() -> Unit): Movie = MoviesBuilder().apply(block).build()