package com.example.android.recentgithubcommits

import com.example.android.recentgithubcommits.data.CommitsRepository
import com.example.android.recentgithubcommits.di.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject


class GitHubCommitsApplication : DaggerApplication() {

    @Inject
    lateinit var commitsRepository: CommitsRepository

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}