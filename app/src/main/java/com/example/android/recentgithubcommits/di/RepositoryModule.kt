package com.example.android.recentgithubcommits.di

import com.example.android.recentgithubcommits.data.CommitDataSource
import com.example.android.recentgithubcommits.data.CommitsRepository
import com.example.android.recentgithubcommits.data.CommitsRepositoryInterface
import com.example.android.recentgithubcommits.data.local.CommitDao
import com.example.android.recentgithubcommits.data.local.CommitsLocalDataSource
import com.example.android.recentgithubcommits.data.remote.CommitsRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
class RepositoryModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class CommitsLocalDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class CommitsRemoteDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class Repository

    @Singleton
    @CommitsLocalDataSource
    @Provides
    fun provideLocalDataSource(commitDao: CommitDao): CommitDataSource {
        return CommitsLocalDataSource(commitDao)
    }

    @Singleton
    @CommitsRemoteDataSource
    @Provides
    fun provideRemoteDataSource(retrofitInterface: RetrofitInterface): CommitDataSource {
        return CommitsRemoteDataSource(retrofitInterface)
    }

    @Singleton
    @Repository
    @Provides
    fun provideRepository(
        @CommitsLocalDataSource localDataSource: CommitDataSource,
        @CommitsRemoteDataSource remoteDataSource: CommitDataSource
    ): CommitsRepositoryInterface {
        return CommitsRepository(localDataSource, remoteDataSource)
    }
}