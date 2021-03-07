package com.example.netflixclone.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.netflixclone.Adapter.MoviesAdapter
import com.example.netflixclone.Model.Movie
import com.example.netflixclone.R
import com.example.netflixclone.ViewModel.MoviesViewModel
import com.example.netflixclone.databinding.ActivityListaFilmesBinding
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaFilmes : AppCompatActivity() {

    private lateinit var binding: ActivityListaFilmesBinding

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFilmesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        moviesViewModel.getMovies()
        movieListObservable()
        setupList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            moviesViewModel.getMovies()
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
        binding.recyclerview.adapter = moviesAdapter
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarMoviesList
        toolbar.setBackgroundColor(getColor(R.color.black_80))
        toolbar.navigationIcon = getDrawable(R.drawable.ic_netflix_official_logo)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        toolbar.inflateMenu(R.menu.main_menu)
    }

    private fun goToDetailsMovie(movie: Movie) {
        moviesViewModel.getMovies()
        val intent = Intent(this, DetalhesFilme::class.java)
        intent.putExtra(Movie.EXTRA_KEY, movie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                backToLoginScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun backToLoginScreen(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}
