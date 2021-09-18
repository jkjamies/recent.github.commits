package com.example.android.recentgithubcommits.network

import com.example.android.recentgithubcommits.models.CommitObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): Call<List<CommitObject>>
}