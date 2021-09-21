package com.example.android.recentgithubcommits.di

import android.app.Application
import com.example.android.recentgithubcommits.GitHubCommitsApplication
import com.example.android.recentgithubcommits.data.CommitsRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        RetrofitModule::class,
        CommitDatabaseModule::class,
        CommitDaoModule::class,
        RepositoryModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<GitHubCommitsApplication> {

//    fun getTasksRepository(): CommitsRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}