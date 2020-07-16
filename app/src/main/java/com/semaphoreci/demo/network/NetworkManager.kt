package com.semaphoreci.demo.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.semaphoreci.demo.network.remotedatasource.RepoRemoteDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val baseUrl = "api.github.com"

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://$baseUrl")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    val repoRemoteDataSource: RepoRemoteDataSource by lazy {
        client.create(RepoRemoteDataSource::class.java)
    }
}
