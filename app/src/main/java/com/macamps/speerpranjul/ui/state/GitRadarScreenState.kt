package com.macamps.speerpranjul.ui.state

import com.macamps.speerpranjul.model.SearchResponse

data class GitRadarScreenState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val data: SearchResponse? = null,
)