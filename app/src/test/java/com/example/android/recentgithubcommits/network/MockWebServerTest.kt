package com.example.android.recentgithubcommits.network

import com.example.android.recentgithubcommits.di.RetrofitInterface
import com.example.android.recentgithubcommits.network.util.GetCommitsMockWebServerHelper
import com.example.android.recentgithubcommits.network.util.MockWebServerTestHelper.provideRetrofit
import com.example.android.recentgithubcommits.network.util.MockWebServerTestHelper.setResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class MockWebServerTest {
    private var mockWebServer: MockWebServer = MockWebServer()
    private lateinit var api: RetrofitInterface

    @Before
    fun setup() {
        mockWebServer.start(8080)
        api = provideRetrofit(mockWebServer).create(RetrofitInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GetCommits should not be empty if response code 200`() = runBlocking {
        val getCommits = GetCommitsMockWebServerHelper(api)
        mockWebServer.setResponse("github_response.json")
        val commitsList = getCommits.execute()
        assert(commitsList.isNotEmpty())
    }

    @Test
    fun `GetCommits should Throw Error if response code 404`() = runBlocking {
        val getCommits = GetCommitsMockWebServerHelper(api)
        mockWebServer.setResponse("github_response.json", 404)
        try {
            val commitsList = getCommits.execute()
            assert(false)
        } catch (e: Exception) {
            assert(true)
        }
    }

}