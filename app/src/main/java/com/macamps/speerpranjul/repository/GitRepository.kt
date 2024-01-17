package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import kotlinx.coroutines.flow.Flow


// Data layer implementation for [GitRepository]
interface GithubRepoMapper {
    suspend fun getUsersBySearch(searchQuery: String): Flow<SearchResponse>
    suspend fun getLoggedInUser(): Flow<User>
}