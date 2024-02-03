package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.network.Api
import com.macamps.speerpranjul.network.searchUserUrl
import com.macamps.speerpranjul.network.userDetails
import com.macamps.speerpranjul.network.userFollowingUrl
import io.ktor.client.statement.HttpResponse

object GithubDataSourceImpl : GithubDataSource {
    override suspend fun searchGithubUser(searchQuery: String): HttpResponse =
        Api.getUserBySearchApi(
            url = searchUserUrl, search = searchQuery
        )

    override suspend fun githubUserFollower(url: String): HttpResponse = Api.getApiByUrl(url)
    override suspend fun githubUserFollowing(): HttpResponse = Api.getApiByUrl(userFollowingUrl)
    override suspend fun getUserWithLoginId(loginId: String): HttpResponse =
        Api.getUserByLoginId(loginId)
}

