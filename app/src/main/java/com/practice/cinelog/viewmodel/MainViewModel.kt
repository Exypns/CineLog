package com.practice.cinelog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.cinelog.data.remote.api.MovieApiService
import com.practice.cinelog.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            val movies = repository.getTrending()
            Log.d("HOME_TEST", "Total: ${movies.size}")
            movies.forEach { Log.d("HOME_TEST", "Title: $it") }
        }
    }
}