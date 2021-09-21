package com.example.android.recentgithubcommits.data

import com.example.android.recentgithubcommits.models.Author
import com.example.android.recentgithubcommits.models.Commit
import com.example.android.recentgithubcommits.models.CommitObject
import com.example.android.recentgithubcommits.data.Result.Success
import com.example.android.recentgithubcommits.data.util.FakeDataSource
import com.example.android.recentgithubcommits.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class DefaultCommitRepositoryTest {

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
        commitsRepository = CommitsRepository(commitRemoteDataSource, commitLocalDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
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