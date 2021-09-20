package com.example.android.recentgithubcommits.network.util

import com.example.android.recentgithubcommits.di.RetrofitInterface

class GetCommitsMockWebServerHelper(private val api: RetrofitInterface) {
    suspend fun execute()  = api.getCommits("jkjamies", "python-helloworld")
}