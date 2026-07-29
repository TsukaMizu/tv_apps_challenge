package com.example.tvapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapps.data.model.ShowDto
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowListViewModel(
    private val repository: TvRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<ShowDto>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<ShowDto>>> = _uiState.asStateFlow()

    init {
        fetchShows()
    }

    fun fetchShows(page: Int = 0) {
        viewModelScope.launch {
            _uiState.value = Resource.Loading
            try {
                val shows = repository.getShows(page)
                _uiState.value = Resource.Success(shows)
            } catch (e: Exception) {
                _uiState.value = Resource.Error(
                    e.message ?: "Failed to load shows. Please try again."
                )
            }
        }
    }
}