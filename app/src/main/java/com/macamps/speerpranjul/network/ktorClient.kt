package com.macamps.speerpranjul.network

import android.os.Build
import com.macamps.speerpranjul.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.time.Duration

private val ktorClient: HttpClient = HttpClient(OkHttp) {

    install(Logging) {
        level = LogLevel.ALL
    }

    defaultRequest {
        header("Accept", "application/vnd.github+json")
        header(
            "Authorization",
            "Bearer ${BuildConfig.githubApiKey}"
        )
    }
    engine {
        config {
            readTimeout(Duration.ofMinutes(5))
            connectTimeout(Duration.ofMinutes(5))
        }
        //   addInterceptor(HeaderInterceptor())

    }

}


object Api {
    suspend fun get(endPoints: String = "") = ktorClient.get {
        url("https://api.github.com/users/pranjulflows")


    }

}

