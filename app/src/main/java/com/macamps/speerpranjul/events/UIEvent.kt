package com.macamps.speerpranjul.events

sealed class UIEvent {
    data class SearchGithubUserByName(val text: String) : UIEvent()
    data class GetSearchedUsers(val response: Any) : UIEvent()
}