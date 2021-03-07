package com.example.netflixclone.di

import com.example.netflixclone.Data.MoviesRepository
import com.example.netflixclone.ViewModel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        MoviesViewModel(get())
    }

    factory {
        MoviesRepository()
    }
}