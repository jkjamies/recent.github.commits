package com.example.android.recentgithubcommits

import android.app.Application
import androidx.lifecycle.*
import com.example.android.recentgithubcommits.di.RetrofitInterface
import com.example.android.recentgithubcommits.models.CommitObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var service: RetrofitInterface

    private var _commitLiveDataList: MutableLiveData<List<CommitObject>> = MutableLiveData()
    val commitLiveDataList: LiveData<List<CommitObject>> = _commitLiveDataList

    init {
        (application as GitHubCommitsApplication).getRetrofitComponent().inject(this)
    }

    fun commitsApiCall() {
        val call: Call<List<CommitObject>> = service.getCommits("jkjamies", "python-helloworld")
        call.enqueue(object : Callback<List<CommitObject>> {
            override fun onResponse(
                call: Call<List<CommitObject>>,
                response: Response<List<CommitObject>>
            ) {
                if (response.isSuccessful) {
                    Timber.d(response.message())
                    _commitLiveDataList.value = response.body()?.toList()
                }
            }

            override fun onFailure(call: Call<List<CommitObject>>, t: Throwable) {
                Timber.d(t.message)
                _commitLiveDataList.value = listOf()
            }
        })
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