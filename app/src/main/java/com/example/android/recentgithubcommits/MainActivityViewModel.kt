package com.example.android.recentgithubcommits

import android.app.Application
import androidx.lifecycle.*
import com.example.android.recentgithubcommits.di.RetrofitInterface
import com.example.android.recentgithubcommits.models.CommitObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var service: RetrofitInterface

    private val _repositoryOwner = MutableLiveData<RepositoryOwner>()
    val repositoryOwner: LiveData<RepositoryOwner>
        get() = _repositoryOwner

    private var _commitLiveDataList: MutableLiveData<List<CommitObject>> = MutableLiveData()
    val commitLiveDataList: LiveData<List<CommitObject>> = _commitLiveDataList

    init {
        (application as GitHubCommitsApplication).getRetrofitComponent().inject(this)
        _repositoryOwner.value = RepositoryOwner("jkjamies", "recent.github.commits")
    }

    fun commitsApiCall() {
        viewModelScope.launch {
            val call: Call<List<CommitObject>> = service.getCommits(_repositoryOwner.value?.ownerName ?: "jkjamies", _repositoryOwner.value?.repositoryName ?: "recent.github.commits")
            try {
                val response = call.awaitResponse()
                if (!response.isSuccessful) {
                    Timber.d(response.message())
                    _commitLiveDataList.postValue(listOf())
                }
                _commitLiveDataList.postValue(response.body()?.toList())
            } catch (ex: Exception) {
                Timber.d(ex)
                _commitLiveDataList.postValue(listOf())
            }
        }
    }
}