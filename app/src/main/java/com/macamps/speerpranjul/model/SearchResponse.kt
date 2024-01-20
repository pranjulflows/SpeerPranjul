package com.macamps.speerpranjul.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items")
    val items: ArrayList<User>,
    @SerialName("total_count")
    val totalCount: Int
)