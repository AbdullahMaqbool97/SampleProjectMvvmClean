package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.notifications

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class NotificationsListResponse(
    @SerializedName("results") var results: NotificationResults? = NotificationResults()

) : Parcelable

@Parcelize
data class DataNotification(

    @SerializedName("requestId" ) var requestId : String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("templateKey") var templateKey: String? = null

) : Parcelable

@Parcelize
data class Notifications(

    @SerializedName("notificationId") var notificationId: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("timeStamp") var timeStamp: Long? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("data") var data: DataNotification? = DataNotification(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("placeholders") var placeholders: Placeholders? = Placeholders(),

    ) : Parcelable

@Parcelize
data class DataItems(

    @SerializedName("notifications") var notifications: ArrayList<Notifications> = arrayListOf(),
    @SerializedName("date") var date: String? = null

) : Parcelable

@Parcelize
data class Translations(

    @SerializedName("en") var en: En? = En(),
    @SerializedName("cr") var cr: Cr? = Cr(),
    @SerializedName("fr") var fr: Fr? = Fr()

) : Parcelable


@Parcelize
data class NotificationTemplates(

    @SerializedName("key") var key: String? = null,
    @SerializedName("serviceName") var serviceName: String? = null,
    @SerializedName("translations") var translations: Translations? = Translations(),
    @SerializedName("type") var type: String? = null,
    @SerializedName("version") var version: Int? = null,
    @SerializedName("id") var id: String? = null

) : Parcelable

@Parcelize
data class NotificationResults(

    @SerializedName("status") var status: String? = null,
    @SerializedName("dataItems") var dataItems: ArrayList<DataItems> = arrayListOf(),
    @SerializedName("totalRecords") var totalRecords: Int? = null,
    @SerializedName("retrieved") var retrieved: Int? = null,
    @SerializedName("notificationTemplates") var notificationTemplates: ArrayList<NotificationTemplates> = arrayListOf()
) : Parcelable

@Parcelize
data class En(

    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null

) : Parcelable


@Parcelize
data class Cr(

    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null

) : Parcelable


@Parcelize
data class Fr(

    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null

) : Parcelable

@Parcelize
data class Placeholders(

    @SerializedName("body") var body: ArrayList<Body> = arrayListOf(),
    @SerializedName("title") var title: ArrayList<String> = arrayListOf()

) : Parcelable

@Parcelize
data class Body(

    @SerializedName("key") var key: String? = null,
    @SerializedName("value") var value: String? = null

) : Parcelable


@Parcelize
data class NotificationDataAdapter(

    @SerializedName("key") var key: String? = null,
    @SerializedName("value") var value: String? = null

) : Parcelable

