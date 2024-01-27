package com.macamps.speerpranjul.repository

import android.util.Log
import com.macamps.speerpranjul.model.CurrentUser
import com.macamps.speerpranjul.model.GithubUserDetails
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.model.UserFollowers
import com.macamps.speerpranjul.network.baseUrl
import com.macamps.speerpranjul.network.userFollowingUrl
import com.macamps.speerpranjul.utils.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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

        Log.e("TAG", "observerUserDetails: followers $followerCount")
        val followingCount = flow<Int?> {
            val result =
                GithubDataSourceImpl.githubUserFollower("$userFollowingUrl${user.login}/following")
            when (result.status) {
                HttpStatusCode.OK -> emit(result.body<List<UserFollowers>?>()?.size ?: 0)
                HttpStatusCode.NotFound -> {
                    Log.e("TAG", "observerUserDetails: Not Found ${result.body<String>()}")
                }

                else -> {
                    throw Exception("Something Went Wrong! http response code: ${result.status}")
                }
            }
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
            subscriptionsUrl = user.subscriptionsUrl,
            type = user.type,
            url = user.url
        )
        followerCount.combine(followingCount) { follows, following ->
            Log.e("TAG", "observerUserDetails: follows $follows following $following")
            githubUserDetails.followersCount = (follows ?: 0).toLong()

            githubUserDetails.followingCount = (following ?: 0).toLong()
        }.collect()
        return flow {
            emit(Resource.Success(githubUserDetails))
        }
    }

    override suspend fun getUserByLoginId(loginId: String): Flow<Resource<User>> {
        return flow {
            val result = GithubDataSourceImpl.getUserWithLoginId(loginId)

            when (result.status) {
                HttpStatusCode.OK -> emit(Resource.Success(result.body<User>()))
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


}
