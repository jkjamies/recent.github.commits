package com.example.android.recentgithubcommits

import android.app.Application
import com.example.android.recentgithubcommits.di.DaggerRetrofitComponent
import com.example.android.recentgithubcommits.di.RetrofitComponent
import com.example.android.recentgithubcommits.di.RetrofitModule
import timber.log.Timber

class GitHubCommitsApplication: Application() {

    private lateinit var retrofitComponent: RetrofitComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        retrofitComponent = DaggerRetrofitComponent.builder()
            .retrofitModule(RetrofitModule())
            .build()
    }

    fun getRetrofitComponent(): RetrofitComponent {
        return retrofitComponent
    }
}