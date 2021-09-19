package com.example.android.recentgithubcommits.util

import com.example.android.recentgithubcommits.di.RetrofitInterface

class GetCommitsMockWebServerHelper(private val api: RetrofitInterface) {
    fun execute() = api.getCommits("jkjamies", "python-helloworld").execute().body()!!
}