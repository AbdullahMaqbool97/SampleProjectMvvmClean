package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.notifications


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GetNotificationDetailRes(
    @SerializedName("results")
    val results: Results
) : Parcelable


@Parcelize
data class Results(
    @SerializedName("data")
    val `data`: NotificationData,
    @SerializedName("message")
    val message: String
) : Parcelable



@Parcelize
data class DataX(
    @SerializedName("body")
    val body: String
) : Parcelable


@Parcelize
data class NotificationData(
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: String
) : Parcelable