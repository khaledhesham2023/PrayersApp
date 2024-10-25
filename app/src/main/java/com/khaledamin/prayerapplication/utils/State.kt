package com.khaledamin.prayerapplication.utils

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : State<T>(data = data)
    class Error<T>(message: String, data: T? = null) : State<T>(data = data, message = message)
}