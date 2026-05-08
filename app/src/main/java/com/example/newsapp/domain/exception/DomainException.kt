package com.example.newsapp.domain.exception

sealed class DomainException(message: String) : Exception(message) {
    class NetworkException : DomainException("No internet connection")
    class TimeoutException : DomainException("Request timeout")
    class ServerException : DomainException("Server error")
    class ParseException : DomainException("Failed to parse response")
    class UnknownException(message: String) : DomainException(message)
}