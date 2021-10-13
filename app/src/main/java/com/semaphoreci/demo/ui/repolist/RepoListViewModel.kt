package com.semaphoreci.demo.ui.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.semaphoreci.demo.network.NetworkManager
import com.semaphoreci.demo.network.models.Repo
import com.semaphoreci.demo.ui.util.Resource

class RepoListViewModel : ViewModel() {
    val repos = liveData<Resource<List<Repo>>> {
        emit(Resource.Loading())

        val reposResponse = NetworkManager.repoRemoteDataSource
            .getSemaphoreRepos()

        when (reposResponse) {
            is NetworkResponse.Success -> {
                emit(Resource.Success(reposResponse.body))
            }
            is NetworkResponse.ServerError -> {
                emit(Resource.Error(reposResponse.body?.message))
            }
            is NetworkResponse.NetworkError -> {
                emit(Resource.Error(reposResponse.error.message))
            }
            is NetworkResponse.UnknownError -> {
                emit(Resource.Error(reposResponse.error.message))
            }
        }
    }
}
