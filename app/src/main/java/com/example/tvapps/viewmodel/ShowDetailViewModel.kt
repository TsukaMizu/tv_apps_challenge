package com.example.tvapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.ui.detail.ShowDetailUi
import com.example.tvapps.ui.detail.toDetailUi
import com.example.tvapps.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowDetailViewModel(
    private val repository: TvRepository,
    private val showId: Int
) : ViewModel() {

    private val _detailState = MutableStateFlow<Resource<ShowDetailUi>>(Resource.Loading)
    val detailState: StateFlow<Resource<ShowDetailUi>> = _detailState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _detailState.value = Resource.Loading
            try {
                // Ambil detail show dan cast secara paralel biar lebih cepat
                val (show, cast) = coroutineScope {
                    val showDeferred = async { repository.getShowDetail(showId) }
                    val castDeferred = async { repository.getCast(showId) }
                    showDeferred.await() to castDeferred.await()
                }
                _detailState.value = Resource.Success(show.toDetailUi(cast))
            } catch (e: Exception) {
                _detailState.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }

    companion object {
        fun factory(repository: TvRepository, showId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShowDetailViewModel(repository, showId) as T
                }
            }
    }
}