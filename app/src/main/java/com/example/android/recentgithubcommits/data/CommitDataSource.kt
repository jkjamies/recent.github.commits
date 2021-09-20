package com.example.android.recentgithubcommits.data

import androidx.lifecycle.LiveData
import com.example.android.recentgithubcommits.models.CommitObject

interface CommitDataSource {
    fun observeCommits(): LiveData<Result<List<CommitObject>>>

    suspend fun insertCommits(commitObjects: List<CommitObject>)

    suspend fun getCommits(owner: String, repo: String): Result<List<CommitObject>>

    suspend fun clear()
}