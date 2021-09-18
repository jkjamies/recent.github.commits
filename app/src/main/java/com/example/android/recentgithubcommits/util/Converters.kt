package com.example.android.recentgithubcommits.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return null
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return null
    }
}