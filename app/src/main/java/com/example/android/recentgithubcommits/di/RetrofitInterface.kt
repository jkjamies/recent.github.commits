package com.example.android.recentgithubcommits.di

import com.example.android.recentgithubcommits.models.CommitObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): List<CommitObject>
}