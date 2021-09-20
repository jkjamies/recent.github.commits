package com.example.android.recentgithubcommits.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.recentgithubcommits.data.CommitDataSource
import com.example.android.recentgithubcommits.data.Result
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.Result.Error
import com.example.android.recentgithubcommits.di.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommitsRemoteDataSource @Inject constructor(private val retrofit: RetrofitInterface) :
    CommitDataSource {

    private val observableCommits = MutableLiveData<Result<List<CommitObject>>>()

    override fun observeCommits(): LiveData<Result<List<CommitObject>>> {
        return observableCommits
    }

    override suspend fun insertCommits(commitObjects: List<CommitObject>) {
        observableCommits.value = Success(commitObjects)
    }

    override suspend fun getCommits(owner: String, repo: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = retrofit.getCommits(owner, repo)
            Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e)
        }
    }

    override suspend fun clear() {
        observableCommits.value = Success(listOf())
    }
}