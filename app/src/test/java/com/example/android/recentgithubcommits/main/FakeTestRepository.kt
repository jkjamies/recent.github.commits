package com.example.android.recentgithubcommits.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.Result.Error
import com.example.android.recentgithubcommits.data.Result
import com.example.android.recentgithubcommits.data.CommitsRepositoryInterface
import com.example.android.recentgithubcommits.models.CommitObject
import kotlinx.coroutines.runBlocking
import java.util.LinkedHashMap

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeTestRepository : CommitsRepositoryInterface {
    var commitsServiceData: LinkedHashMap<String, CommitObject> = LinkedHashMap()

    private var shouldReturnError = false

    private val observableCommits = MutableLiveData<Result<List<CommitObject>>>()

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun observeCommits(): LiveData<Result<List<CommitObject>>> {
        runBlocking { refreshCommits("jkjamies", "recent.github.commits") }
        return observableCommits
    }

    override suspend fun getCommits(
        owner: String,
        repo: String,
        forceUpdate: Boolean
    ): Result<List<CommitObject>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(commitsServiceData.values.toList())
    }

    override suspend fun insertCommits(commitObjects: List<CommitObject>) {
        commitObjects.forEach {
            commitsServiceData[it.url] = it
        }
    }

    override suspend fun refreshCommits(owner: String, repo: String) {
        observableCommits.value = getCommits("jkjamies", "recent.github.commits", true)
    }

    override suspend fun clear() {
        commitsServiceData.clear()
        refreshCommits("jkjamies", "recent.github.commits")
    }

    fun addCommits(vararg commits: CommitObject) {
        for (commit in commits) {
            commitsServiceData[commit.url] = commit
        }
        runBlocking { refreshCommits("jkjamies", "recent.github.commits") }
    }
}