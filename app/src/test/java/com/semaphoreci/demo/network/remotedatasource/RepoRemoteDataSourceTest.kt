package com.semaphoreci.demo.network.remotedatasource

import com.haroldadmin.cnradapter.NetworkResponse
import com.semaphoreci.demo.network.errors.ServerError
import com.semaphoreci.demo.network.models.Repo
import com.semaphoreci.demo.util.NetworkTestUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoRemoteDataSourceTest {
    private lateinit var mockServer: MockWebServer

    private lateinit var remoteDataSource: RepoRemoteDataSource

    private val successResponse =
        NetworkTestUtil.getFixtureResponse("RepoList.json")
    private val errorResponse = NetworkTestUtil.getServerErrorResponse()

    @Before
    fun setup() {
        mockServer = MockWebServer()
        remoteDataSource = NetworkTestUtil
            .getNetworkClient(mockServer)
            .create(RepoRemoteDataSource::class.java)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun getSemaphoreRepos_should_use_correct_endpoint() = runBlocking {
        // arrange
        mockServer.enqueue(successResponse)
        // act
        remoteDataSource.getSemaphoreRepos()
        val request = withContext(Dispatchers.IO) {
            mockServer.takeRequest()
        }
        // assert
        assertEquals(request.path, "/orgs/semaphoreci-demos/repos")
    }

    @Test
    fun getSemaphoreRepos_should_return_repos_list() = runBlocking {
        // arrange
        mockServer.enqueue(successResponse)
        // act
        val response = remoteDataSource.getSemaphoreRepos()
        withContext(Dispatchers.IO) { mockServer.takeRequest() }
        // assert
        assertTrue(response is NetworkResponse.Success<List<Repo>>)
    }

    @Test
    fun getSemaphoreRepos_should_return_server_error() = runBlocking {
        // arrange
        mockServer.enqueue(errorResponse)
        // act
        val response = remoteDataSource.getSemaphoreRepos()
        withContext(Dispatchers.IO) { mockServer.takeRequest() }
        // assert
        assertTrue(response is NetworkResponse.ServerError<ServerError>)
    }
}
