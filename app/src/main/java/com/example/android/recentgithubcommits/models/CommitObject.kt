package com.example.android.recentgithubcommits.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits_table")
data class CommitObject(
    @PrimaryKey val url: String,
    val sha: String,
    @Embedded val commit: Commit
)