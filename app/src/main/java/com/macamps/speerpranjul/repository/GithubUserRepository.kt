package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import kotlinx.coroutines.flow.Flow

class GithubUserRepository : GithubRepoMapper {
    override suspend fun getUsersBySearch(searchQuery: String): Flow<SearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getLoggedInUser(): Flow<User> {
        TODO("Not yet implemented")
    }
}