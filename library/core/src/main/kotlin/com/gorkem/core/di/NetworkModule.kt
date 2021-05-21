package com.gorkem.core.di

import com.gorkem.core.data.interceptor.TheMovieInterceptor
import com.gorkem.core.di.qualifier.ApiUrl
import com.gorkem.core.di.qualifier.Debug
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(@Debug isDebug: Boolean): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Timber.tag("TheMovie OkHttp").d(message)
        }.apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        theMovieInterceptor: TheMovieInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(theMovieInterceptor)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiUrl apiUrl: String,
        okhttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okhttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()
}
