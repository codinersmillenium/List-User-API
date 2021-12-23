package com.example.submission2.Model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var name: String? ="",
    var username: String? ="",
    var photo: String? = "",
    var company: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var followers: Int = 0,
    var following: Int = 0
) : Parcelable