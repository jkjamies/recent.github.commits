package com.example.android.recentgithubcommits.di

import com.example.android.recentgithubcommits.data.local.CommitDao
import com.example.android.recentgithubcommits.data.CommitsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommitDaoModule {
    @Singleton
    @Provides
    fun provideCommitDao(database: CommitsDatabase) : CommitDao {
        return database.commitDao()
    }
}