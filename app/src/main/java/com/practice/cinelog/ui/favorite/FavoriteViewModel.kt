package com.practice.cinelog.ui.favorite

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

data class FavoritesUiState(
    val movies: List<MovieEntity> = emptyList(),
    val isEmpty: Boolean = false
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavorite().collect { movies ->
                _uiState.value = FavoritesUiState(
                    movies  = movies,
                    isEmpty = movies.isEmpty()
                )
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            repository.removeFromFavorite(movieId)
        }
    }
}