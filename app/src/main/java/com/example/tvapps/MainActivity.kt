package com.example.tvapps

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.tvapps.data.remote.NetworkModule
import com.example.tvapps.data.repository.TvRepositoryImpl
import com.example.tvapps.util.Resource
import com.example.tvapps.viewmodel.ShowListViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var vm: ShowListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ShowListViewModel(
            TvRepositoryImpl(NetworkModule.api)
        )

        lifecycleScope.launch {

            testApi()
            testRepository()

            vm.showState.collect { state ->

                when (state) {

                    is Resource.Loading -> {
                        Log.d("VM_STATE", "Loading")
                    }

                    is Resource.Success -> {
                        Log.d(
                            "VM_STATE",
                            "Success: ${state.data.size}"
                        )
                    }

                    is Resource.Error -> {
                        Log.e(
                            "VM_STATE",
                            "Error: ${state.message}"
                        )
                    }
                }
            }
        }

        setContent {

        }

        Log.d("TEST_API", "onCreate jalan")
    }


    private suspend fun testApi() {
        Log.d("TEST_API", "testApi mulai")

        try {
            val shows = NetworkModule.api.getShows()

            Log.d(
                "TEST_API",
                "Total shows: ${shows.size}"
            )

            shows.take(5).forEach {
                Log.d("TEST_API", it.name)
            }

        } catch (e: Exception) {
            Log.e(
                "TEST_API",
                "Error: ${e.message}",
                e
            )
        }
    }


    private suspend fun testRepository() {

        Log.d(
            "TEST_REPOSITORY",
            "repo run"
        )

        val repository = TvRepositoryImpl(
            NetworkModule.api
        )

        val result = repository.getShows()

        Log.d(
            "TEST_REPOSITORY",
            "Total shows: ${result.size}"
        )

        result.take(5).forEach {
            Log.d(
                "TEST_REPOSITORY",
                it.name
            )
        }
    }
}