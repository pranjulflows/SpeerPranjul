package com.macamps.speerpranjul.events

// Todo: implement UI Event
sealed class UIEvent {
    data class SearchGithubUserByName(val text: String) : UIEvent()
    data class GetSearchedUsers(val response: Any) : UIEvent()
}