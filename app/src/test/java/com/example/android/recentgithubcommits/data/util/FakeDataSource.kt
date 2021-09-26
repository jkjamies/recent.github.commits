package com.example.android.recentgithubcommits.data.util

import androidx.lifecycle.LiveData
import com.example.android.recentgithubcommits.data.CommitDataSource
import com.example.android.recentgithubcommits.data.Result
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.Result.Error

class FakeDataSource(var commits: MutableList<CommitObject>? = mutableListOf()) : CommitDataSource {
    override fun observeCommits(): LiveData<Result<List<CommitObject>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCommits(commitObjects: List<CommitObject>) {
        commits?.addAll(commitObjects)
    }

    override suspend fun getCommits(owner: String, repo: String): Result<List<CommitObject>> {
        commits?.let { return Success(ArrayList(it)) }
        return Error(
            Exception("Commits not found")
        )
    }

    override suspend fun clear() {
        commits?.clear()
    }
}