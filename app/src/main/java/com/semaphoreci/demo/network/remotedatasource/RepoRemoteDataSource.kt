package com.semaphoreci.demo.network.remotedatasource

import com.haroldadmin.cnradapter.NetworkResponse
import com.semaphoreci.demo.network.errors.ServerError
import com.semaphoreci.demo.network.models.Repo
import retrofit2.http.GET

interface RepoRemoteDataSource {
    @GET("/orgs/semaphoreci-demos/repos")
    suspend fun getSemaphoreRepos(): NetworkResponse<List<Repo>, ServerError>
}
