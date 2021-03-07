package com.example.netflixclone.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.Data.MoviesRepository
import com.example.netflixclone.Model.Movie
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel(){

    val moviesObservable: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies(){
        viewModelScope.launch {
            val movies = repository.getMovies()
            moviesObservable.postValue(movies)
        }
    }
}
