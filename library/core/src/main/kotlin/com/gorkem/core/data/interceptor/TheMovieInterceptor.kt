package com.gorkem.core.data.interceptor

import com.gorkem.core.di.qualifier.ApiKey
import com.gorkem.core.util.languageTag
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

const val API_KEY = "api_key"
const val LANGUAGE = "language"

class TheMovieInterceptor @Inject constructor(@ApiKey val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, apiKey)
            .addQueryParameter(LANGUAGE, languageTag())
            .build()

        val newRequest = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}
