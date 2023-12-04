package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
open class BaseResponse(
    @Json(name = "isSuccessful")
    var isSuccessful: Boolean = false,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "responseCode")
    var responseCode: Int? = null
) : Parcelable