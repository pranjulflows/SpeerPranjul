package com.macamps.speerpranjul.ui.state

import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.utils.Resource

data class GitRadarScreenState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val data: SearchResponse? = null,
)