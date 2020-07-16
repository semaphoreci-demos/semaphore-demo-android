package com.semaphoreci.demo.network.models

import java.io.Serializable
import java.util.*

data class Repo(
    val createdAt: Date,
    val updatedAt: Date,
    val name: String,
    val description: String?,
    val language: String?,
    val htmlUrl: String,
    val homepage: String?,
    val watchersCount: Int,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int
) : Serializable
