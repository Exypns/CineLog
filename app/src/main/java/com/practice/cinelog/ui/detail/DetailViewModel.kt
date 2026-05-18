package com.practice.cinelog.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.cinelog.data.repository.MovieRepository
import com.practice.cinelog.data.repository.WatchlistRepository
import com.practice.cinelog.domain.model.MovieDetail
import com.practice.cinelog.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val movie: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isInWatchlist: Boolean = false,
    val isInFavorites: Boolean = false
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = movieRepository.getMovieDetail(movieId)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        movie     = result.data,
                        isLoading = false
                    )
                    observeWatchlistStatus(movieId)
                }
                is NetworkResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading    = false,
                    errorMessage = result.message
                )
                else -> Unit
            }
        }
    }

    private fun observeWatchlistStatus(movieId: Int) {
        viewModelScope.launch {
            watchlistRepository.isInWatchlist(movieId).collect { inWatchlist ->
                _uiState.value = _uiState.value.copy(isInWatchlist = inWatchlist)
            }
        }
        viewModelScope.launch {
            watchlistRepository.isInFavorite(movieId).collect { inFavorites ->
                _uiState.value = _uiState.value.copy(isInFavorites = inFavorites)
            }
        }
    }

    fun toggleWatchlist() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            if (_uiState.value.isInWatchlist) {
                watchlistRepository.removeFromWatchlist(movie.id)
            } else {
                watchlistRepository.addToWatchlist(movie)
            }
        }
    }

    fun toggleFavorite() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            if (_uiState.value.isInFavorites) {
                watchlistRepository.removeFromFavorite(movie.id)
            } else {
                watchlistRepository.addToFavorite(movie)
            }
        }
    }
}