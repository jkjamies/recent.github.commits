package com.example.android.recentgithubcommits

import android.app.Application
import androidx.lifecycle.*
import com.example.android.recentgithubcommits.di.RetrofitInterface
import com.example.android.recentgithubcommits.models.CommitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var service: RetrofitInterface

    private var _commitLiveDataList: MutableLiveData<List<CommitObject>> = MutableLiveData()
    val commitLiveDataList: LiveData<List<CommitObject>> = _commitLiveDataList

    init {
        (application as GitHubCommitsApplication).getRetrofitComponent().inject(this)
    }

    suspend fun commitsApiCall() {
        val call: Call<List<CommitObject>> = service.getCommits("jkjamies", "python-helloworld")
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

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("unchecked_cast")
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}