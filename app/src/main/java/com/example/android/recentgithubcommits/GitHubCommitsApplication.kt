package com.example.android.recentgithubcommits

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import com.example.android.recentgithubcommits.data.CommitsRepository
import com.example.android.recentgithubcommits.di.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject


class GitHubCommitsApplication : DaggerApplication() {

    @Inject
    lateinit var commitsRepository: CommitsRepository
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build();
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

//    @VisibleForTesting
//    fun getCommitsRepository(): CommitsRepository {
//        return commitsRepository
//    }
}