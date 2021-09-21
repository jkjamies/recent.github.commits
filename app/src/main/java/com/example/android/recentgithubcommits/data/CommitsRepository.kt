package com.example.android.recentgithubcommits.data

import androidx.lifecycle.LiveData
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.util.wrapEspressoIdlingResource
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.Result.Error
import com.example.android.recentgithubcommits.di.RepositoryModule.CommitsLocalDataSource
import com.example.android.recentgithubcommits.di.RepositoryModule.CommitsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitsRepository @Inject constructor(
    @CommitsLocalDataSource private val localDataSource: CommitDataSource,
    @CommitsRemoteDataSource private val remoteDataSource: CommitDataSource
) : CommitsRepositoryInterface {

    override fun observeCommits(): LiveData<Result<List<CommitObject>>> {
        wrapEspressoIdlingResource {
            return localDataSource.observeCommits()
        }
    }

    override suspend fun getCommits(forceUpdate: Boolean, owner: String, repo: String): Result<List<CommitObject>> {
        wrapEspressoIdlingResource {
            if (forceUpdate) {
                try {
                    updateCommitsFromRemoteDataSource(owner, repo)
                } catch (ex: Exception) {
                    return Error(ex)
                }
            }
            return localDataSource.getCommits(owner, repo)
        }
    }

    override suspend fun insertCommits(commitObjects: List<CommitObject>) {
        wrapEspressoIdlingResource {
            coroutineScope {
                launch { remoteDataSource.insertCommits(commitObjects) }
                launch { localDataSource.insertCommits(commitObjects) }
            }
        }
    }

    override suspend fun refreshCommits(forceUpdate: Boolean, owner: String, repo: String) {
        wrapEspressoIdlingResource {
            updateCommitsFromRemoteDataSource(owner, repo)
        }
    }

    override suspend fun clear() {
        wrapEspressoIdlingResource {
            withContext(Dispatchers.IO) {
                coroutineScope {
                    launch { remoteDataSource.clear() }
                    launch { localDataSource.clear() }
                }
            }
        }
    }

    private suspend fun updateCommitsFromRemoteDataSource(owner: String, repo: String) {
        wrapEspressoIdlingResource {
            val remoteCommits = remoteDataSource.getCommits(owner, repo)

            if (remoteCommits is Success) {
                // Real apps might want to do a proper sync.
                localDataSource.clear()
                localDataSource.insertCommits(remoteCommits.data)
            } else if (remoteCommits is Error) {
                throw remoteCommits.exception
            }
        }
    }
}