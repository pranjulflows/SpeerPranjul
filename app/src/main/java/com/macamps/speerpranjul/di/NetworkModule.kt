package com.macamps.speerpranjul.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.time.Duration
import javax.inject.Singleton


object NetworkModule {

    fun ktorClient(): HttpClient = HttpClient(OkHttp) {
//    install(Logging)
        engine {
            config {
                readTimeout(Duration.ofMinutes(5))
                connectTimeout(Duration.ofMinutes(5))
            }
            //   addInterceptor(HeaderInterceptor())

        }

    }
}