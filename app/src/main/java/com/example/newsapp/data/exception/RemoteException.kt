package com.example.newsapp.data.exception

sealed class RemoteException(message: String) : Exception(message) {
    class NoInternetException : RemoteException("No internet connection")
    class TimeoutException : RemoteException("Request timeout")
    class ServerException(status: String) : RemoteException("Server error: $status")
    class ParseException : RemoteException("Failed to parse response")
    class UnknownException(message: String) : RemoteException(message)
}