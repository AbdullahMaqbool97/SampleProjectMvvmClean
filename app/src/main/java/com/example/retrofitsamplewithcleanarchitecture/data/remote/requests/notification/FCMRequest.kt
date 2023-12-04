package com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.notification


data class FCMRequest(
    val token: String,
    val userId: String
)