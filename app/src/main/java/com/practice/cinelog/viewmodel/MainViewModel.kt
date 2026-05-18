package com.practice.cinelog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.cinelog.data.remote.api.MovieApiService
import com.practice.cinelog.data.repository.MovieRepository
import com.practice.cinelog.util.NetworkResult
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
            when (val result = repository.getTrending()) {
                is NetworkResult.Success -> Log.d("HOME_TEST", "Movies: ${result.data.map { it.title }}")
                is NetworkResult.Error   -> Log.e("HOME_TEST", "Error: ${result.message}")
                is NetworkResult.Loading -> Unit
            }
        }
    }
}