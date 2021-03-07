package com.example.netflixclone.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixclone.Model.Movie
import com.example.netflixclone.databinding.ListMoviesBinding
import com.squareup.picasso.Picasso

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val allMovies: MutableList<Movie> = mutableListOf()
    private var onItemClickListener: (Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val binding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount() = allMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allMovies[position])
    }

    inner class ViewHolder(val binding: ListMoviesBinding, val onItemClickListener: (Movie) -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie) {
            val movieCover = movie.movieCover
            if(!movieCover.equals("null") && !movieCover.isNullOrEmpty()){
                Picasso.get().load(movie.movieCover).fit().into(binding.movieCover)
            }
            binding.root.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }

    fun updateList(movies: List<Movie>){
        with(allMovies){
            allMovies.clear()
            allMovies.addAll(movies)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: (Movie) -> Unit){
        this.onItemClickListener = onItemClickListener
    }
}
