package com.example.android.recentgithubcommits.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.recentgithubcommits.util.*
import com.example.android.recentgithubcommits.util.commit1
import com.example.android.recentgithubcommits.util.commit2
import com.example.android.recentgithubcommits.util.commit3
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainFragmentViewModelTest {

    // Subject under test
    private lateinit var viewModel: MainFragmentViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var commitsRepository: FakeTestRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        commitsRepository = FakeTestRepository()
        commitsRepository.addCommits(commit1, commit2, commit3)

        viewModel = MainFragmentViewModel(commitsRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCommits_forced_setsLiveData() {
        // When getting commits
        viewModel.getCommits(true)

        // Then the live data is represented correctly
        val value = viewModel.commitLiveDataList.getOrAwaitValue()

        assert(value.containsAll(listOf(commit1, commit2, commit3)))
    }

    @Test
    fun getCommits_notForced_setsLiveData() {
        // When getting commits
        viewModel.getCommits(false)

        // Then the live data is represented correctly
        val value = viewModel.commitLiveDataList.getOrAwaitValue()

        assert(value.containsAll(listOf(commit1, commit2, commit3)))
    }

}