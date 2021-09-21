package com.example.android.recentgithubcommits.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.recentgithubcommits.util.MainCoroutineRule
import com.example.android.recentgithubcommits.util.commit1
import com.example.android.recentgithubcommits.util.commit2
import com.example.android.recentgithubcommits.util.commit3
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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
        // We initialise the tasks to 3, with one active and two completed
        commitsRepository = FakeTestRepository()
        commitsRepository.addCommits(commit1, commit2, commit3)

//        viewModel = MainFragmentViewModel(commitsRepository)
    }
}