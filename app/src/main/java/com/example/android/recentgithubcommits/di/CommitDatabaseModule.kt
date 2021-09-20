package com.example.android.recentgithubcommits.di

import android.content.Context
import androidx.room.Room
import com.example.android.recentgithubcommits.data.CommitsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommitDatabaseModule {
    @Singleton
    @Provides
    fun provideCommitDao(context: Context) : CommitsDatabase {
        return Room.databaseBuilder(
            context,
            CommitsDatabase::class.java, "commits_database"
        ).build()
    }
}