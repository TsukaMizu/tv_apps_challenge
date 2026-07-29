package com.example.tvapps

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.tvapps.data.remote.NetworkModule
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            testApi()
        }

        setContent {
            // Temporary UI
        }
        Log.d("TEST_API", "onCreate jalan")
    }

    private suspend fun testApi() {
        Log.d("TEST_API", "testApi mulai")
        try {
            val shows = NetworkModule.api.getShows()

            Log.d("TEST_API", "Total shows: ${shows.size}")
            shows.take(5).forEach {
                Log.d("TEST_API", it.name)
            }
        } catch (e: Exception) {
            Log.e("TEST_API", "Error: ${e.message}", e)
        }
    }
}