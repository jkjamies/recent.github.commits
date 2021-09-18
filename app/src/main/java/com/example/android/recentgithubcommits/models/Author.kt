package com.example.android.recentgithubcommits.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Author(
    val name: String,
    val email: String,
    val date: Date
) : Parcelable