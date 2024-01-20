package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.network.Api
import com.macamps.speerpranjul.network.searchUserUrl
import io.ktor.client.statement.HttpResponse

object GithubDataSourceImpl : GithubDataSource {
    override suspend fun searchGithubUser(searchQuery: String):HttpResponse = Api.getUserBySearchApi(
        url = searchUserUrl, search = searchQuery
    )
}

