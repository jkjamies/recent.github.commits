package com.example.android.recentgithubcommits

import android.app.Application
import timber.log.Timber

class GitHubCommitsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}