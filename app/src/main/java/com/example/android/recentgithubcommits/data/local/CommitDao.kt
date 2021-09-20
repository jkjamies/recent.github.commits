package com.example.android.recentgithubcommits.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.recentgithubcommits.models.CommitObject

@Dao
interface CommitDao {
    @Query("SELECT * FROM commits_table")
    fun observeCommits(): LiveData<List<CommitObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommits(commitObjects: List<CommitObject>)

    @Query("SELECT * FROM commits_table ORDER BY date DESC")
    fun getCommits(): List<CommitObject>

    @Query("DELETE FROM commits_table")
    fun clear()
}