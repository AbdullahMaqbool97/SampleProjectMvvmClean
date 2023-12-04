package com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.notification

data class GetNotificationDetailReq(
    val notificationId: String,
    val userId: String
    )