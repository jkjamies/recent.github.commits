package com.example.android.recentgithubcommits

import android.app.Application
import com.example.android.recentgithubcommits.di.*
import timber.log.Timber

class GitHubCommitsApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .retrofitModule(RetrofitModule())
            .commitDatabaseModule(CommitDatabaseModule())
            .commitDaoModule(CommitDaoModule())
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}