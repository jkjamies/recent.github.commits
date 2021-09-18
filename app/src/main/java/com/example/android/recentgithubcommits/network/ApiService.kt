package com.example.android.recentgithubcommits.network

import com.example.android.recentgithubcommits.models.CommitObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

private const val BASE_URL = "https://api.github.com/"

private val moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(HttpClient.getClient())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): Call<List<CommitObject>>
}

object GitHubApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}