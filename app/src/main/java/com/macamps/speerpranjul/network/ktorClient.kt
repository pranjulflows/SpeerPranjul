package com.macamps.speerpranjul.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.time.Duration

private val ktorClient: HttpClient = HttpClient(OkHttp) {
    install(Logging)
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
        header("Accept", "application/vnd.github+json")
        header(
            "Authorization",
            "Bearer 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"
        )

    }

}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = Request.Builder()
        req.apply {
            header("Accept", "application/vnd.github+json")
            header(
                "Authorization",
                "Bearer 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"
            )
        }

        return chain.proceed(req.build())
    }


}