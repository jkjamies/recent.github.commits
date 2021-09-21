package com.example.android.recentgithubcommits.data

import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.util.FakeDataSource
import com.example.android.recentgithubcommits.util.MainCoroutineRule
import com.example.android.recentgithubcommits.util.commit1
import com.example.android.recentgithubcommits.util.commit2
import com.example.android.recentgithubcommits.util.commit3
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommitRepositoryTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(dispatcher)

    private val remoteCommits =
        listOf(commit1, commit2).sortedBy { remoteCommits -> remoteCommits.url }
    private val localCommits = listOf(commit3).sortedBy { localCommits -> localCommits.url }

    private lateinit var commitRemoteDataSource: FakeDataSource
    private lateinit var commitLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var commitsRepository: CommitsRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        commitRemoteDataSource = FakeDataSource(remoteCommits.toMutableList())
        commitLocalDataSource = FakeDataSource(localCommits.toMutableList())
        // Get a reference to the class under test.
        commitsRepository = CommitsRepository(commitLocalDataSource, commitRemoteDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCommits_requestsAllCommitsFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // When commits are requested from the commits repository
        val commits = commitsRepository.getCommits(
            "jkjamies",
            "recent.github.commits"
        ) as Success

        // Then commits are loaded from the remote data source
        assertThat(commits.data, IsEqual(localCommits))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun refreshCommits_requestsAllCommitsFromRemoteDataSource() =
        mainCoroutineRule.runBlockingTest {
            // When commits are requested from the commits repository
            commitsRepository.refreshCommits(
                "jkjamies",
                "recent.github.commits"
            )

            // force update false to grab from local database
            val commits = commitsRepository.getCommits(
                "jkjamies",
                "recent.github.commits"
            ) as Success

            // Then commits are equal to remote commits because of fetch from local db
            assertThat(commits.data, IsEqual(remoteCommits))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun clear_clearsOutAllCommitsFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // When commits are requested from the commits repository
        testScope.launch {
            commitsRepository.clear()

            // force update false to grab from local database
            val commits = commitsRepository.getCommits(
                "jkjamies",
                "recent.github.commits"
            ) as Success

            // Then commits are equal to remote commits because of fetch from local db
            assertThat(commits.data, IsEqual(listOf()))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertCommits_InsertsAllCommitsIntoLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // When commits are requested from the commits repository
        testScope.launch {
            commitsRepository.insertCommits(remoteCommits)

            // force update false to grab from local database
            val commits = commitsRepository.getCommits(
                "jkjamies",
                "recent.github.commits"
            ) as Success

            // Then commits are equal to remote commits because of inserting that list
            assertThat(commits.data, IsEqual(remoteCommits))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCommits_requestsAllCommitsFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // When commits are requested from the commits repository
        val commits = commitsRepository.getCommits(
            "jkjamies",
            "recent.github.commits",
            true
        ) as Success

        // Then commits are loaded from the remote data source
        assertThat(commits.data, IsEqual(remoteCommits))
    }
}