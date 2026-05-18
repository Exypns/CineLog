package com.practice.cinelog.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.cinelog.data.local.db.MovieEntity
import com.practice.cinelog.data.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WatchlistUiState(
    val movies: List<MovieEntity> = emptyList(),
    val isEmpty: Boolean = false
)

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWatchlist().collect { movies ->
                _uiState.value = WatchlistUiState(
                    movies  = movies,
                    isEmpty = movies.isEmpty()
                )
            }
        }
    }

    fun removeFromWatchlist(movieId: Int) {
        viewModelScope.launch {
            repository.removeFromWatchlist(movieId)
        }
    }
}