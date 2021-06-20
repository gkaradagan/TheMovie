package com.gorkem.common.di

import android.content.Context
import androidx.room.Room
import com.gorkem.common.data.local.CommonDatabase
import com.gorkem.common.data.local.FavouriteConverters
import com.gorkem.common.data.local.dao.FavouriteProgramDao
import com.gorkem.common.data.repository.FavouriteProgramRepository
import com.gorkem.common.data.repository.FavouriteProgramRepositoryImpl
import com.gorkem.common.domain.interactor.FavouriteProgramDeleteUseCase
import com.gorkem.common.domain.interactor.FavouriteProgramGetUseCase
import com.gorkem.common.domain.interactor.FavouriteProgramInsertUseCase
import com.gorkem.core.util.AppCoroutineDispatchers
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideCommonDatabase(
        @ApplicationContext appContext: Context,
        moshi: Moshi,
    ): CommonDatabase {
        FavouriteConverters.initialize(moshi)
        return Room.databaseBuilder(
            appContext,
            CommonDatabase::class.java,
            "Common"
        ).build()
    }

    @Provides
    fun provideFavouriteProgramDao(appDatabase: CommonDatabase): FavouriteProgramDao =
        appDatabase.favouriteProgramDao()
}

@Module
@InstallIn(ViewModelComponent::class)
class CommonViewModule {

    @Provides
    fun provideFavouriteProgramInsertUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        repository: FavouriteProgramRepository,
    ): FavouriteProgramInsertUseCase =
        FavouriteProgramInsertUseCase(appCoroutineDispatchers, repository)

    @Provides
    fun provideFavouriteProgramGetUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        repository: FavouriteProgramRepository,
    ): FavouriteProgramGetUseCase =
        FavouriteProgramGetUseCase(appCoroutineDispatchers, repository)

    @Provides
    fun provideFavouriteProgramDeleteUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        repository: FavouriteProgramRepository,
    ): FavouriteProgramDeleteUseCase =
        FavouriteProgramDeleteUseCase(appCoroutineDispatchers, repository)

    @Provides
    fun provideFavouriteProgramRepository(
        favouriteProgramDao: FavouriteProgramDao,
    ): FavouriteProgramRepository =
        FavouriteProgramRepositoryImpl(favouriteProgramDao)
}
