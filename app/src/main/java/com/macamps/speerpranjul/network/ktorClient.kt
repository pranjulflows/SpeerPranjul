package com.macamps.speerpranjul.network

import android.util.Log
import com.macamps.speerpranjul.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private fun ktorClient() = HttpClient(Android) {

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger", message)
            }
        }
        level = LogLevel.BODY

    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true // Optional: Ignore extra fields in the JSON response
            coerceInputValues = true // Coerce nulls to default values
        })

    }

    ResponseObserver { response ->
        Log.d("http_status:", "${response.status.value}")

    }

    defaultRequest {
        header(HttpHeaders.Accept, "application/vnd.github+json")
        header(
            "Authorization",
            "Bearer ${BuildConfig.githubApiKey}"
        )
    }
    engine {
        connectTimeout = 10_000
        socketTimeout = 10_000
        //   addInterceptor(HeaderInterceptor())

    }

}


object Api {
    suspend fun getUserBySearchApi(url: String, search: String = "") = ktorClient().get {
        url("$url?q=$search")
    }

}

const val baseUrl = "https://api.github.com/"
const val searchUserUrl = "${baseUrl}search/users"
const val currentUserUrl = "${baseUrl}/user"