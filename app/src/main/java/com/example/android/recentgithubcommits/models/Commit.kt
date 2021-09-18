package com.example.android.recentgithubcommits.models

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Commit(
    @Embedded val author: Author,
    val message: String
) : Parcelable