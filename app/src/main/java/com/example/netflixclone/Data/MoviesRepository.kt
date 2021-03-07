package com.example.netflixclone.Data

import android.util.Log
import com.example.netflixclone.Model.Movie
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class MoviesRepository {

    private val db = Firebase.firestore

    suspend fun getMovies(): List<Movie>{
        return suspendCoroutine {
            db.collection("movies").get()
                .addOnSuccessListener { result ->
                    val movies = result.map {
                        Movie(
                            movieCover = it.get("movie_cover").toString(),
                            name = it.get("name").toString(),
                            description = it.get("description").toString(),
                            cast = it.get("cast").toString(),
                            moviePlayerCover = it.get("movie_player_cover").toString(),
                            movieVideo = it.get("movie_video").toString()
                        )
                    }
                    //continua a execução após mapear tudo
                    it.resume(movies)
                }.addOnFailureListener { Log.println(Log.ERROR, "ERROR", "DB Failed") }
        }
    }
}
