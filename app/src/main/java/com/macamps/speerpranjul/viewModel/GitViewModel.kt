package com.macamps.speerpranjul.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macamps.speerpranjul.model.GithubUserDetails
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.model.UserFollowers
import com.macamps.speerpranjul.repository.GithubUserRepository
import com.macamps.speerpranjul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitViewModel @Inject internal constructor() : ViewModel() {

    private val githubUserRepository = GithubUserRepository()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _apiErrorResponse = MutableStateFlow<String?>(null)
    private val apiErrorResponse = _apiErrorResponse.asStateFlow()
    private var _githubUsersList = MutableStateFlow<SearchResponse?>(null)
    private var _githubUserDetails = MutableStateFlow<GithubUserDetails?>(null)
    private var _followerFollowingUsers = MutableStateFlow<List<User>?>(null)
    var followerFollowingUsers = _followerFollowingUsers.asStateFlow()
    val githubUsersList = _githubUsersList.asStateFlow()
    val githubUserDetails = _githubUserDetails.asStateFlow()

    fun getUsersFollowerFollowers(loginId: String,type:String) {
        viewModelScope.launch {
            when(type){
                "following"->{
                    githubUserRepository.getUserFollowing(loginId).onStart {
                        _isLoading.update { true }
                    }.catch {
                        _isLoading.update { false }

                    }.collect { response ->
                        when (response) {
                            is Resource.Loading -> _isLoading.update { true }
                            is Resource.Error -> _apiErrorResponse.update { response.message }
                            is Resource.Success -> _followerFollowingUsers.update { response.data }
                        }

                    }
                }
                else->{
                    githubUserRepository.getUserFollowers(loginId).onStart {
                        _isLoading.update { true }
                    }.catch {
                        _isLoading.update { false }

                    }.collect { response ->
                        when (response) {
                            is Resource.Loading -> _isLoading.update { true }
                            is Resource.Error -> _apiErrorResponse.update { response.message }
                            is Resource.Success -> _followerFollowingUsers.update { response.data }
                        }
                    }
                }

            }


        }
    }

    fun searchGithubUsers(search: String) {
        viewModelScope.launch {
            githubUserRepository.getUsersBySearch(search).onStart {
                _isLoading.update { true }

            }.catch {
                Log.e("TAG", "getNewsList: ${it.localizedMessage}")
                _isLoading.update { false }

            }.collect { response ->
                when (response) {
                    is Resource.Loading -> _isLoading.update { true }
                    is Resource.Error -> _apiErrorResponse.update { response.message }
                    is Resource.Success -> _githubUsersList.update { response.data }
                }
                _isLoading.update { false }

            }

        }

    }

    fun getUserWithLoginId(loginId: String) {
        viewModelScope.launch {
            githubUserRepository.getUserByLoginId(loginId).onStart {
                _isLoading.update { true }

            }.catch {
                Log.e("TAG", "getUserWithLoginId: ${it.localizedMessage}")

            }.collect { response ->
                when (response) {
                    is Resource.Loading -> _isLoading.update { true }
                    is Resource.Error -> _apiErrorResponse.update { response.message }
                    is Resource.Success -> response.data?.let { getUserDetails(it) }

                }
                _isLoading.update { false }

            }

        }
    }

    private fun getUserDetails(user: User) {
        viewModelScope.launch {
            githubUserRepository.observerUserDetails(user).onStart {
                _isLoading.update { true }

            }.catch {
                Log.e("TAG", "getNewsList: ${it.localizedMessage}")

            }.collect { response ->
                when (response) {
                    is Resource.Loading -> _isLoading.update { true }
                    is Resource.Error -> _apiErrorResponse.update { response.message }
                    is Resource.Success -> _githubUserDetails.update { response.data }
                }
                _isLoading.update { false }

            }

        }
    }

}