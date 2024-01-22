package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.model.CurrentUser
import com.macamps.speerpranjul.model.GithubUserDetails
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.model.UserFollowers
import com.macamps.speerpranjul.utils.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubUserRepository : GithubRepoMapper {
    override suspend fun getUsersBySearch(searchQuery: String): Flow<Resource<SearchResponse>> {

        return flow {
            val result = GithubDataSourceImpl.searchGithubUser(searchQuery = searchQuery)
            when (result.status) {
                HttpStatusCode.OK -> emit(Resource.Success(result.body<SearchResponse>()))
                HttpStatusCode.BadRequest -> emit(
                    Resource.Error(
                        "Bad Request", data = result.body()
                    )
                )

                HttpStatusCode.Unauthorized -> emit(
                    Resource.Error(
                        "Unauthorized", data = result.body()
                    )
                )

                HttpStatusCode.InternalServerError -> emit(
                    Resource.Error(
                        "500:Internal Server Error", data = result.body()
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLoggedInUser(): Flow<Resource<CurrentUser>> {
        TODO("Not yet implemented")
    }

    override suspend fun observerUserDetails(user: User): Flow<Resource<GithubUserDetails>> {
        val followerCount = flow<Int?> {
            val result = GithubDataSourceImpl.githubUserFollower(user.followersUrl)
            when (result.status) {
                HttpStatusCode.OK -> emit(result.body<List<UserFollowers>?>()?.size ?: 0)
                else -> {
                    throw Exception("Something Went Wrong! http response code: ${result.status}")
                }
            }
        }.flowOn(Dispatchers.IO)

        val followingCount = flow<Int?> {
            val result = GithubDataSourceImpl.githubUserFollower(user.followingUrl)
            when (result.status) {
                HttpStatusCode.OK -> emit(result.body<List<UserFollowers>?>()?.size ?: 0)
                else -> {
                    throw Exception("Something Went Wrong! http response code: ${result.status}")
                }
            }
            GithubDataSourceImpl.githubUserFollower(user.followingUrl)
                .body<List<UserFollowers>?>()?.size ?: 0
        }.flowOn(Dispatchers.IO)
        val githubUserDetails = GithubUserDetails(
            avatarUrl = user.avatarUrl,
            eventsUrl = user.eventsUrl,
            gistsUrl = user.gistsUrl,
            gravatarId = user.gravatarId,
            htmlUrl = user.htmlUrl,
            id = user.id,
            login = user.login,
            nodeId = user.nodeId,
            reposUrl = user.reposUrl,
            organizationsUrl = user.organizationsUrl,
            receivedEventsUrl = user.receivedEventsUrl,
            score = user.score,
            siteAdmin = user.siteAdmin,
            starredUrl = user.starredUrl,
            subscriptionsUrl = user.subscriptionsUrl, type = user.type,
            url = user.url
        )

        followerCount.combine(followingCount) { follows, following ->
            githubUserDetails.followersCount = follows?.toLong()
            githubUserDetails.followingCount = following?.toLong()
        }
        return flow {
            emit(Resource.Success(githubUserDetails))
        }
    }

    override suspend fun getUserFollowers(): Flow<Resource<List<UserFollowers>>> =
        flow<Resource<List<UserFollowers>>> {

        }.flowOn(Dispatchers.IO)

    override suspend fun getUserFollowing(): Flow<Resource<List<UserFollowers>>> {
        TODO("Not yet implemented")
    }
}
