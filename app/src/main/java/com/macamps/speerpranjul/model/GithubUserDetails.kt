package com.macamps.speerpranjul.model

import kotlinx.serialization.SerialName

data class GithubUserDetails(
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("events_url")
    val eventsUrl: String?,
    var followersCount: Long?=0,
    var followingCount: Long?=0,
    @SerialName("gists_url")
    val gistsUrl: String?,
    @SerialName("gravatar_id")
    val gravatarId: String?,
    @SerialName("html_url")
    val htmlUrl: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("login")
    val login: String,
    @SerialName("node_id")
    val nodeId: String?,
    @SerialName("organizations_url")
    val organizationsUrl: String?,
    @SerialName("received_events_url")
    val receivedEventsUrl: String?,
    @SerialName("repos_url")
    val reposUrl: String?,
    @SerialName("score")
    val score: Double?,
    @SerialName("site_admin")
    val siteAdmin: Boolean = false,
    @SerialName("starred_url")
    val starredUrl: String?,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?
)

