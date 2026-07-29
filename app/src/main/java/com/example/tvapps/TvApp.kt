package com.example.tvapps

import android.app.Application
import com.example.tvapps.data.remote.NetworkModule
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.data.repository.TvRepositoryImpl

/**
 * Application class used as a simple manual DI container.
 * Exposes a single, app-wide [TvRepository] instance so screens/ViewModels
 * don't need to construct their own Retrofit/OkHttp stack.
 */
class TvApp : Application() {

    lateinit var repository: TvRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = TvRepositoryImpl(NetworkModule.api)
    }
}
