package com.example.android.recentgithubcommits

import android.app.Application
import com.example.android.recentgithubcommits.di.*
import timber.log.Timber

class GitHubCommitsApplication : Application() {

    private lateinit var retrofitComponent: RetrofitComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        retrofitComponent = DaggerRetrofitComponent.builder()
            .applicationModule(ApplicationModule(this))
            .retrofitModule(RetrofitModule())
            .commitDatabaseModule(CommitDatabaseModule())
            .commitDaoModule(CommitDaoModule())
            .build()
    }

    fun getRetrofitComponent(): RetrofitComponent {
        return retrofitComponent
    }
}