package com.example.netflixclone.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.netflixclone.Adapter.MoviesAdapter
import com.example.netflixclone.Model.Movie
import com.example.netflixclone.R
import com.example.netflixclone.ViewModel.MoviesViewModel
import com.example.netflixclone.databinding.ActivityDetalhesFilmeBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetalhesFilme : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesFilmeBinding

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = intent.getParcelableExtra<Movie>(Movie.EXTRA_KEY)

        binding = ActivityDetalhesFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        setupToolbar()
        moviesViewModel.getMovies()
        populateMovieDetails(movie)
        movieListObservable()
        setupList()
        setupVideoPlayer(movie)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            moviesViewModel.getMovies()
        }
    }

    private fun setupVideoPlayer(movie: Movie?) {
        binding.btnPlay.setOnClickListener {
            startVideo(movie)
        }
        binding.playVideo.setOnClickListener {
            startVideo(movie)
        }
    }

    private fun movieListObservable() {
        moviesViewModel.moviesObservable.observe(this, {
            moviesAdapter.updateList(it)
        })
    }

    private fun setupList() {
        moviesAdapter.setOnItemClickListener { movie ->
            goToDetailsMovie(movie)
        }
        binding.recyclerMoreMovies.adapter = moviesAdapter
    }

    private fun goToDetailsMovie(movie: Movie) {
        moviesViewModel.getMovies()
        val intent = Intent(this, DetalhesFilme::class.java)
        intent.putExtra(Movie.EXTRA_KEY, movie)
        startActivity(intent)

        if(binding.txtTitle.text.equals(movie.name)){
            finish()
        }
    }

    private fun populateMovieDetails(movie: Movie?) {
        val moviePlayerCover = movie?.moviePlayerCover
        if(!moviePlayerCover.equals("null")){
            Picasso.get().load(moviePlayerCover).fit().into(binding.cover)
        }

        binding.txtTitle.text = movie?.name
        binding.txtDescription.text = movie?.description
        binding.txtElenco.text = movie?.cast
    }

    private fun startVideo(movie: Movie?) {
        val intent = Intent(this, Video::class.java)
        intent.putExtra(Movie.EXTRA_KEY, movie)
        startActivity(intent)
    }

    private fun setupToolbar(){
        val toolbarDetails = binding.toolbarDetails
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        toolbarDetails.navigationIcon = getDrawable(R.drawable.ic_baseline_arrow_back_24)
        toolbarDetails.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
