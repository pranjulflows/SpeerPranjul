package com.macamps.speerpranjul.repository

import io.ktor.client.statement.HttpResponse

interface GithubDataSource {
    suspend fun searchGithubUser(searchQuery: String): HttpResponse
}