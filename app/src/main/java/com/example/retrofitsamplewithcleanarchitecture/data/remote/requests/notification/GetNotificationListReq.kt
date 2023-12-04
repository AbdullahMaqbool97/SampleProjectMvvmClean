package com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.notification

data class GetNotificationListReq(
    val userId: String,
    val skip: Int,
    val limit: Int,
)
