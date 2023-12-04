package com.example.retrofitsamplewithcleanarchitecture.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthRequest(
    @SerializedName("grant_type")
    val grant_type: String,
    @SerializedName("scope")
    val scope: String,
) : Parcelable
