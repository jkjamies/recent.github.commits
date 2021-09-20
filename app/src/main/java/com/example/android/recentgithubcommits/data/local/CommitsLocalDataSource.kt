package com.example.android.recentgithubcommits.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.android.recentgithubcommits.data.CommitDataSource
import com.example.android.recentgithubcommits.data.Result
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.Result.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommitsLocalDataSource @Inject constructor(private val commitDao: CommitDao) :
    CommitDataSource {

    override fun observeCommits(): LiveData<Result<List<CommitObject>>> {
        return commitDao.observeCommits().map {
            Success(it)
        }
    }

    override suspend fun insertCommits(commitObjects: List<CommitObject>) = withContext(Dispatchers.IO) {
        commitDao.insertCommits(commitObjects)
    }

    override suspend fun getCommits(owner: String, repo: String): Result<List<CommitObject>> = withContext(Dispatchers.IO) {
        try {
            Success(commitDao.getCommits())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun clear() = withContext(Dispatchers.IO) {
        commitDao.clear()
    }
}