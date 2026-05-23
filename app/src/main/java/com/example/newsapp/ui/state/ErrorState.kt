package com.example.newsapp.ui.state

data class ErrorState(
    val message: String,
    val errorType: ErrorType = ErrorType.GENERIC
)

enum class ErrorType {
    NETWORK,
    PARSING,
    SERVER,
    GENERIC
}