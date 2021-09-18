package com.example.android.recentgithubcommits.network

import com.example.android.recentgithubcommits.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

private val moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(HttpClient.getClient())
    .baseUrl(BASE_URL)
    .build()

object GitHubApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}