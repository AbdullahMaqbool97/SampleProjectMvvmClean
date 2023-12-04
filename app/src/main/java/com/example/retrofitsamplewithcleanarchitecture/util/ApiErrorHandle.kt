package com.example.retrofitsamplewithcleanarchitecture.util

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * This class trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns a [ErrorModel]
 *
 * */

class ApiErrorHandle @Inject constructor() {

    fun traceErrorException(throwable: Throwable?): ErrorModel {

        val errorModel: ErrorModel? = when (throwable) {
            is HttpException -> {
                when {
                    throwable.code() == 401 -> {
                        ErrorModel(
                            throwable.message(),
                            throwable.code(),
                            ErrorModel.ErrorStatus.UNAUTHORIZED
                        )
                    }
                    throwable.code() == 400 -> {
                        val jObjError = JSONObject(throwable.response()?.errorBody()!!.string())
                        val jsonArray = jObjError.getJSONArray("errors")
                        val zero_index: JSONObject = jsonArray.getJSONObject(0)
                        val message = zero_index.getString("message")
                        var errorCode = ""
                        if (zero_index.has("errorCode")) {
                            errorCode = zero_index.getString("errorCode")
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE,
                                errorCode
                            )
                        } else {
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE
                            )
                        }
                    }
                    throwable.code() == 409 -> {
                        val jObjError = JSONObject(throwable.response()?.errorBody()!!.string())
                        val jsonArray = jObjError.getJSONArray("errors")
                        val zeroIndex: JSONObject = jsonArray.getJSONObject(0)
                        val message = zeroIndex.getString("message")
                        var errorCode = ""
                        if (zeroIndex.has("errorCode")) {
                            errorCode = zeroIndex.getString("errorCode")
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE,
                                errorCode
                            )
                        } else {
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE
                            )
                        }
                    }
                    throwable.code() == 424 -> {
                        val jObjError = JSONObject(throwable.response()?.errorBody()!!.string())
                        val jsonArray = jObjError.getJSONArray("errors")
                        val zeroIndex: JSONObject = jsonArray.getJSONObject(0)
                        val message = zeroIndex.getString("message")
                        var errorCode = ""
                        if (zeroIndex.has("errorCode")) {
                            errorCode = zeroIndex.getString("errorCode")
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE,
                                errorCode
                            )
                        } else {
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE
                            )
                        }
                    }
                    throwable.code() == 404 -> {
                        val jObjError = JSONObject(throwable.response()?.errorBody()!!.string())
                        val description = jObjError.getString("description")
                        ErrorModel(
                            description,
                            throwable.code(),
                            ErrorModel.ErrorStatus.NOT_FOUND
                        )
                    }
                    throwable.code() == 440 -> {
                        val jObjError = JSONObject(throwable.response()?.errorBody()!!.string())
                        val jsonArray = jObjError.getJSONArray("errors")
                        val zeroIndex: JSONObject = jsonArray.getJSONObject(0)
                        val message = zeroIndex.getString("message")
                        var errorCode = ""
                        if (zeroIndex.has("errorCode")) {
                            errorCode = zeroIndex.getString("errorCode")
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE,
                                errorCode
                            )
                        } else {
                            ErrorModel(
                                message,
                                throwable.code(),
                                ErrorModel.ErrorStatus.BAD_RESPONSE
                            )
                        }
                    }
                    throwable.code() == 500 -> {
                        ErrorModel(
                            "Internal Server Error",
                            throwable.code(),
                            ErrorModel.ErrorStatus.INTERNAL_SERVER_ERROR
                        )
                    }
                    throwable.code() == 502 -> {
                        ErrorModel(
                            "Bad Gateway",
                            throwable.code(),
                            ErrorModel.ErrorStatus.BAD_GATEWAY_ERROR
                        )
                    }
                    throwable.code() == 504 -> {
                        ErrorModel(
                            "504 Gateway Time-out",
                            throwable.code(),
                            ErrorModel.ErrorStatus.BAD_GATEWAY_ERROR
                        )
                    }
                    else -> {
                        getHttpError(throwable.response()?.errorBody())
                    }

                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.TIMEOUT)
            }

            // handle connection error
            is IOException -> {
                ErrorModel("No internet connected", ErrorModel.ErrorStatus.NO_CONNECTION)
            }

            //handle null pointer exception
            is NullPointerException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.NULLPOINTER)
            }
            else -> null
        }
        return errorModel ?: ErrorModel(
            "We are very sorry. we cannot connect to the MyCash server to fetch your account status. Please try again later!",
            0,
            ErrorModel.ErrorStatus.BAD_RESPONSE
        )
    }

    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            ErrorModel(body.toString(), 400, ErrorModel.ErrorStatus.BAD_RESPONSE)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(message = e.message, errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED)
        }

    }
}