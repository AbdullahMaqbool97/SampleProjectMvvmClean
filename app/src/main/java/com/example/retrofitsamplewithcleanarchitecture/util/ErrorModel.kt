package com.example.retrofitsamplewithcleanarchitecture.util


private const val NO_CONNECTION_ERROR_MESSAGE = "No connection!"
private const val BAD_RESPONSE_ERROR_MESSAGE = "Something went wrong!"
private const val TIME_OUT_ERROR_MESSAGE = "Time out! , Cannot connect to Server. Please try again shortly"
private const val EMPTY_RESPONSE_ERROR_MESSAGE = "Something went wrong!"
private const val NOT_DEFINED_ERROR_MESSAGE = "Something went wrong!"
private const val UNAVAILABLE_ERROR_MESSAGE = "Requested Resource not available!"
private const val UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized!"
private const val NOTFOUND_ERROR_MESSAGE = "Requested Resource is not available! "
private const val NULL_POINTER_EXCEPTION_MESSAGE = "Something went wrong!"
private const val INTERNAL_SERVER_ERROR_MESSAGE = "Cannot connect to Server. Please try again shortly"
private const val BAD_GATEWAY_MESSAGE="Bad Gateway Error. Please try again shortly"

data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus,
    var errorCode: String? = ""
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus)

    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)

    fun getErrorMessage(): String {
        return when (errorStatus) {
            ErrorStatus.NO_CONNECTION -> NO_CONNECTION_ERROR_MESSAGE
            ErrorStatus.BAD_RESPONSE -> message.toString()
            ErrorStatus.TIMEOUT -> TIME_OUT_ERROR_MESSAGE
            ErrorStatus.EMPTY_RESPONSE -> EMPTY_RESPONSE_ERROR_MESSAGE
            ErrorStatus.NOT_DEFINED -> NOT_DEFINED_ERROR_MESSAGE
            ErrorStatus.UNAUTHORIZED -> UNAUTHORIZED_ERROR_MESSAGE
            ErrorStatus.NOT_FOUND -> UNAUTHORIZED_ERROR_MESSAGE
            ErrorStatus.UNAVAILABLE -> UNAVAILABLE_ERROR_MESSAGE
            ErrorStatus.NULLPOINTER -> NULL_POINTER_EXCEPTION_MESSAGE
            ErrorStatus.INTERNAL_SERVER_ERROR -> INTERNAL_SERVER_ERROR_MESSAGE
            ErrorStatus.BAD_GATEWAY_ERROR -> BAD_GATEWAY_MESSAGE
        }
    }

    /**
     * various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,

        /**
         * error in getting value (Json Error, Server Error, etc)
         */
        BAD_RESPONSE,

        /**
         * Time out  error
         */
        TIMEOUT,

        /**
         * no data available in repository
         */
        EMPTY_RESPONSE,

        /**
         * an unexpected error
         */
        NOT_DEFINED,

        /**
         * bad credential
         */
        UNAVAILABLE,

        /**
         * unauthorized
         */
        UNAUTHORIZED,

        NOT_FOUND,

        /**
         * null pointer exception
         */
        NULLPOINTER,

        /**
         * Internal server Error
         */
        INTERNAL_SERVER_ERROR,

        /**
         * Bad Gateway Error
         */
        BAD_GATEWAY_ERROR
    }
}