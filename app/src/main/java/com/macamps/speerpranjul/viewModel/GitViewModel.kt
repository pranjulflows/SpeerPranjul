package com.macamps.speerpranjul.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.repository.GithubUserRepository
import com.macamps.speerpranjul.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitViewModel @Inject constructor() : ViewModel() {
    private val githubUserRepository = GithubUserRepository()
    private val _isLoading = MutableStateFlow(false)
    private val _apiErrorResponse = MutableStateFlow<String?>(null)
    private val apiErrorResponse = _apiErrorResponse.asStateFlow()
    private var _githubUsersList = MutableStateFlow<SearchResponse?>(null)
    val githubUsersList = _githubUsersList.asStateFlow()


    fun searchGithubUsers(search: String) {
        viewModelScope.launch {
            githubUserRepository.getUsersBySearch(search).onStart {
                _isLoading.update { true }

            }.catch {
                Log.e("TAG", "getNewsList: ${it.localizedMessage}")
                _isLoading.update { false }

            }.collect { response ->
                when (response) {
                    is Resource.Loading -> _isLoading.update { false }
                    is Resource.Error -> _apiErrorResponse.update { response.message }
                    is Resource.Success -> _githubUsersList.update { response.data }
                }
            }

        }

    }

}