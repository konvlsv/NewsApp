package com.example.newsapp.ui.state

sealed class UiState<T> {
    class Loading<T> : UiState<T>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error<T>(
        val message: String,
        val errorType: ErrorType = ErrorType.GENERIC
    ) : UiState<T>()
}

