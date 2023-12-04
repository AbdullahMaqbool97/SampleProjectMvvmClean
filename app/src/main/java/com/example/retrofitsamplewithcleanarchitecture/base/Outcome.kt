package com.example.retrofitsamplewithcleanarchitecture.base

import com.example.retrofitsamplewithcleanarchitecture.util.ErrorModel

sealed class Outcome<T>{

    class Start<T> : Outcome<T>()

    class End<T> : Outcome<T>()

    class Loading<T> : Outcome<T>()

    class Progress<T>(val data:T?=null): Outcome<T>()

    class Success<T>(val data:T): Outcome<T>()

    class Failure<T>(val e: ErrorModel?) : Outcome<T>()

    class NetworkError<T>(val e: Throwable?) : Outcome<T>()

    class Error<T>(
        val e: Exception? = null,
        var showErrorDialog: Boolean,
        var message: String? = null
    ) : Outcome<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$e]"
            is Loading -> "Loading"
            is Failure -> "${e?.getErrorMessage()}zaza${e?.code.toString()}"
            is Progress -> "Progress"
            is End -> "End"
            is NetworkError -> "Network Error"
            is Start -> "Start"
        }
    }
}
