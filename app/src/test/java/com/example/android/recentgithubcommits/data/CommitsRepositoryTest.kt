package com.example.android.recentgithubcommits.data

import com.example.android.recentgithubcommits.models.Author
import com.example.android.recentgithubcommits.models.Commit
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.util.FakeDataSource
import com.example.android.recentgithubcommits.util.MainCoroutineRule
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
import java.util.*

class DefaultCommitRepositoryTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(dispatcher)

    private val commit1 = CommitObject(
        "url1",
        "sha1",
        Commit(
            Author(
                "name1",
                "email1@email1.com",
                Date(1302796849000)
            ),
            "message1"
        )
    )
    private val commit2 = CommitObject(
        "url2",
        "sha2",
        Commit(
            Author(
                "name2",
                "email2@email2.com",
                Date(1302796849000)
            ),
            "message2"
        )
    )
    private val commit3 = CommitObject(
        "url3",
        "sha3",
        Commit(
            Author(
                "name3",
                "email3@email3.com",
                Date(1302796849000)
            ),
            "message3"
        )
    )
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
            false,
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
                false,
                "jkjamies",
                "recent.github.commits"
            )

            // force update false to grab from local database
            val commits = commitsRepository.getCommits(
                false,
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
                false,
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
                false,
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
            true,
            "jkjamies",
            "recent.github.commits"
        ) as Success

        // Then commits are loaded from the remote data source
        assertThat(commits.data, IsEqual(remoteCommits))
    }
}