package com.example.android.recentgithubcommits.data

import androidx.lifecycle.LiveData
import com.example.android.recentgithubcommits.models.CommitObject

interface CommitsRepositoryInterface {
    fun observeCommits(): LiveData<Result<List<CommitObject>>>

    suspend fun getCommits(owner: String, repo: String, forceUpdate: Boolean = false): Result<List<CommitObject>>

    suspend fun insertCommits(commitObjects: List<CommitObject>)

    suspend fun refreshCommits(owner: String, repo: String)

    suspend fun clear()
}