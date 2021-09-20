package com.example.android.recentgithubcommits.di

import com.example.android.recentgithubcommits.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        RetrofitModule::class,
        CommitDatabaseModule::class,
        CommitDaoModule::class
    ]
)
interface RetrofitComponent {

    fun inject(mainFragment: MainFragment)
}