package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.model.CurrentUser
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.utils.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
}