package com.example.android.recentgithubcommits.main

import androidx.lifecycle.*
import com.example.android.recentgithubcommits.data.CommitsRepositoryInterface
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.di.RepositoryModule.Repository
import com.example.android.recentgithubcommits.main.models.RepositoryOwner
import kotlinx.coroutines.launch

class MainFragmentViewModel(@Repository private val repository: CommitsRepositoryInterface) : ViewModel() {

    private val _repositoryOwner = MutableLiveData<RepositoryOwner>()
    val repositoryOwner: LiveData<RepositoryOwner>
        get() = _repositoryOwner

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _commitLiveDataList: LiveData<List<CommitObject>> =
        _forceUpdate.switchMap { forceUpdate ->
            if (forceUpdate) {
                loading.value = true
                viewModelScope.launch {
                    repository.getCommits(
                        _repositoryOwner.value?.ownerName ?: "jkjamies",
                        _repositoryOwner.value?.repositoryName ?: "recent.github.commits",
                        forceUpdate
                    )
                    loading.value = false
                }
            }
            repository.observeCommits().switchMap { filterCommits(it) }

        }
    val commitLiveDataList: LiveData<List<CommitObject>> = _commitLiveDataList

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _repositoryOwner.value = RepositoryOwner("jkjamies", "recent.github.commits")
    }

    private fun filterCommits(commitsResult: Result<List<CommitObject>>): LiveData<List<CommitObject>> {
        val result = MutableLiveData<List<CommitObject>>()

        if (commitsResult is Success) {
            // here is where you could call for a filter using
            // viewModelScope and only show certain ones

            // add all to arraylist to avoid using !! assert on commitsResult.data
            // this is code smell but I haven't found a cleaner solution (I am not a fan of !!)
            val commits = arrayListOf<CommitObject>()
            commits.addAll(commitsResult.data)
            result.value = commits.toList()
        } else {
            result.value = emptyList()
        }

        return result
    }

    fun getCommits(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    @Suppress("UNCHECKED_CAST")
    class MainFragmentViewModelFactory(
        private val repository: CommitsRepositoryInterface
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (MainFragmentViewModel(repository) as T)
    }
}