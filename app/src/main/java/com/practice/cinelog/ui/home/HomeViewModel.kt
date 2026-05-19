package com.practice.cinelog.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.cinelog.data.repository.MovieRepository
import com.practice.cinelog.domain.model.Movie
import com.practice.cinelog.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val trending: List<Movie> = emptyList(),
    val popular: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList(),
    val topRated: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            val movies = repository.getTrending()
            when (val result = repository.getTrending()) {
                is NetworkResult.Success -> Log.d("HOME_TEST", "Movies: ${result.data.map { it.posterUrl }}")
                is NetworkResult.Error   -> Log.e("HOME_TEST", "Error: ${result.message}")
                is NetworkResult.Loading -> Unit
            }

            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val trending = repository.getTrending()
            val popular = repository.getPopular()
            val nowPlaying = repository.getNowPlaying()
            val upcoming = repository.getUpcoming()
            val topRated = repository.getTopRated()

            _uiState.value = HomeUiState(
                trending = trending.dataOrEmpty(),
                popular = popular.dataOrEmpty(),
                nowPlaying = nowPlaying.dataOrEmpty(),
                upcoming = upcoming.dataOrEmpty(),
                topRated = topRated.dataOrEmpty(),
                isLoading = false,
                errorMessage = trending.errorOrNull()
            )
        }
    }

    fun refresh() = loadMovies()
}

private fun NetworkResult<List<Movie>>.dataOrEmpty() =
    if (this is NetworkResult.Success) data else emptyList()

private fun NetworkResult<List<Movie>>.errorOrNull() =
    if (this is NetworkResult.Error) message else null