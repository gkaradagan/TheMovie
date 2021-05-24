/*
 * Copyright 2021 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
