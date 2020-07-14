package com.semaphoreci.demo.util

import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

object NetworkTestUtil {
    internal fun getNetworkClient(server: MockWebServer): Retrofit {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        return Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    internal fun getFixtureResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap()
    ): MockResponse {
        val source = NetworkTestUtil::javaClass.javaClass.classLoader!!
            .getResourceAsStream("fixtures/$fileName")
            .source()
            .buffer()

        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charset.defaultCharset()))
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        return mockResponse
    }

    internal fun getServerErrorResponse(): MockResponse {
        val source = NetworkTestUtil::javaClass.javaClass.classLoader!!
            .getResourceAsStream("fixtures/ServerError.json")
            .source()
            .buffer()

        return MockResponse()
            .setResponseCode(400)
            .setBody(source.readString(Charset.defaultCharset()))
    }
}
