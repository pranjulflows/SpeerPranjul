package com.macamps.speerpranjul.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser (
    val login: String,
    val id: Long,

    @SerialName("node_id")
    val nodeID: String?,

    @SerialName("avatar_url")
    val avatarURL: String,

    @SerialName("gravatar_id")
    val gravatarID: String,

    val url: String,

    @SerialName("html_url")
    val htmlURL: String,

    @SerialName("followers_url")
    val followersURL: String,

    @SerialName("following_url")
    val followingURL: String,

    @SerialName("gists_url")
    val gistsURL: String,

    @SerialName("starred_url")
    val starredURL: String,

    @SerialName("subscriptions_url")
    val subscriptionsURL: String,

    @SerialName("organizations_url")
    val organizationsURL: String,

    @SerialName("repos_url")
    val reposURL: String,

    @SerialName("events_url")
    val eventsURL: String,

    @SerialName("received_events_url")
    val receivedEventsURL: String,

    val type: String,

    @SerialName("site_admin")
    val siteAdmin: Boolean,

    val name: String,
    val company: String,
    val blog: String,
    val location: String?=null ,
    val email: String?=null,
    val hireable: String? = null,
    val bio: String,

    @SerialName("twitter_username")
    val twitterUsername: String? = null,

    @SerialName("public_repos")
    val publicRepos: Long?,

    @SerialName("public_gists")
    val publicGists: Long?,

    val followers: Long,
    val following: Long,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)
