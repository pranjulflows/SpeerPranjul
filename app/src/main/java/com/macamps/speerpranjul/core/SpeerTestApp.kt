package com.macamps.speerpranjul.core

import android.app.Application
import android.os.StrictMode
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.macamps.speerpranjul.BuildConfig
import dagger.hilt.android.HiltAndroidApp

class SpeerTestApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults()
    }
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .crossfade(true)
            .build()
}