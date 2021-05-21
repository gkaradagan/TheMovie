package com.gorkem.popularmovie.di

import android.content.Context
import androidx.room.Room
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.api.MovieService
import com.gorkem.popularmovie.data.local.PopularMovieFeatureDatabase
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.repository.MovieRepository
import com.gorkem.popularmovie.data.repository.MovieRepositoryImpl
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PopularMovieSingletonModule {

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun providePopularMovieFeatureDatabase(@ApplicationContext appContext: Context): PopularMovieFeatureDatabase =
        Room.databaseBuilder(
            appContext,
            PopularMovieFeatureDatabase::class.java,
            "PopularMovieFeature"
        ).build()

    @Provides
    fun provideMovieGenreDao(appDatabase: PopularMovieFeatureDatabase): MovieGenreDao =
        appDatabase.movieGenreDao()
}

@Module
@InstallIn(ViewModelComponent::class)
class PopularMovieViewModule {

    @Provides
    fun providePopularMovieUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: MovieRepository,
    ): PopularMoviesUseCase =
        PopularMoviesUseCase(appCoroutineDispatchers, api)

    @Provides
    fun provideMovieRepository(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: MovieService,
        movieGenreDao: MovieGenreDao,
    ): MovieRepository =
        MovieRepositoryImpl(appCoroutineDispatchers, api, movieGenreDao)
}
