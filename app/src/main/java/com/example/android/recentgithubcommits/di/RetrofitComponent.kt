package com.example.android.recentgithubcommits.di

import com.example.android.recentgithubcommits.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}