package com.macamps.speerpranjul.repository

import com.macamps.speerpranjul.model.CurrentUser
import com.macamps.speerpranjul.model.GithubUserDetails
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.model.UserFollowers
import com.macamps.speerpranjul.utils.Resource
import kotlinx.coroutines.flow.Flow


/// Data layer implementation for [GithubUserRepository]
interface GithubRepoMapper {
    suspend fun getUsersBySearch(searchQuery: String): Flow<Resource<SearchResponse>>
    suspend fun getLoggedInUser(): Flow<Resource<CurrentUser>>
    suspend fun observerUserDetails(user: User): Flow<Resource<GithubUserDetails>>
    suspend fun getUserByLoginId(loginId: String): Flow<Resource<User>>
    suspend fun getUserFollowing(loginId: String): Flow<Resource<List<User>>>
    suspend fun getUserFollowers(loginId: String): Flow<Resource<List<User>>>
}