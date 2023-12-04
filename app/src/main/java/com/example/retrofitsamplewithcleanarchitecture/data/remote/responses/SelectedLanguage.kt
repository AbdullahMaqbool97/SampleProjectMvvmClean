package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses

import android.os.Parcel
import android.os.Parcelable


class SelectedLanguage() : Parcelable {
    var en: String = ""
    var cr: String = ""
    var fr: String = ""
    var language = "BaseApplication.language"


    val selectedLanguageString: String
        get() {
            return when (language) {
                "en" -> en
                "cr" -> cr
                "fr" -> fr
                else -> en
            }
        }


    constructor(parcel: Parcel) : this() {
        this@SelectedLanguage.en = parcel.readString() ?: ""
        this@SelectedLanguage.cr = parcel.readString() ?: ""
        this@SelectedLanguage.fr = parcel.readString() ?: ""
        language = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(en)
        parcel.writeString(cr)
        parcel.writeString(fr)
        parcel.writeString(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectedLanguage> {
        override fun createFromParcel(parcel: Parcel): SelectedLanguage {
            return SelectedLanguage(parcel)
        }

        override fun newArray(size: Int): Array<SelectedLanguage?> {
            return arrayOfNulls(size)
        }
    }
}