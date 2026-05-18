package com.practice.cinelog.util

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: retrofit2.HttpException) {
        NetworkResult.Error(
            message = e.response()?.errorBody()?.string() ?: "HTTP error",
            code = e.code()
        )
    } catch (e: java.io.IOException) {
        NetworkResult.Error("No internet connection")
    } catch (e: Exception) {
        NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred")
    }
}