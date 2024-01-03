package com.macamps.speerpranjul.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("total_count")
    val totalCount: Long,

    @SerialName("incomplete_results")
    val incompleteResults: Boolean,

    @SerialName("items")
    val items: List<User>
)

