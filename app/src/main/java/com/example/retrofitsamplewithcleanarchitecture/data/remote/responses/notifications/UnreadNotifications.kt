package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.notifications

import com.google.gson.annotations.SerializedName

data class UnreadNotifications(
    @SerializedName("results" ) var results : ResultsNotification? = ResultsNotification()
)


data class Data (

    @SerializedName("count" ) var count : Int? = null

)

data class ResultsNotification (

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : Data?   = Data()

)