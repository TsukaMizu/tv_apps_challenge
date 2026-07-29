package com.example.tvapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tvapps.data.model.ShowDto
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
class ShowListViewModel(
    private val repository: TvRepository
) : ViewModel() {

    private val _showState = MutableStateFlow<Resource<List<ShowDto>>>(Resource.Loading)
    val showState: StateFlow<Resource<List<ShowDto>>> = _showState.asStateFlow()

    init {
        loadShows()
    }

fun loadShows(page: Int = 0) {

    viewModelScope.launch {

        Log.d("SHOW_VM", "loadShows dipanggil")

        _showState.value = Resource.Loading

        try {

            val result = repository.getShows(page)

            Log.d(
                "SHOW_VM",
                "Jumlah data: ${result.size}"
            )

            _showState.value = Resource.Success(result)

        } catch (e: Exception) {

            Log.e(
                "SHOW_VM",
                "ERROR API: ${e.message}",
                e
            )

            _showState.value =
                Resource.Error(
                    e.message ?: "Unknown Error"
                )
        }
    }
}

    companion object {
        fun factory(repository: TvRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShowListViewModel(repository) as T
                }
            }
    }
}
