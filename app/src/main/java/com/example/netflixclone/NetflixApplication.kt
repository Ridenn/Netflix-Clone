package com.example.netflixclone

import android.app.Application
import com.example.netflixclone.di.mainModule
import org.koin.core.context.startKoin

//dura o tempo inteiro do app
class NetflixApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule)
        }
    }
}