package com.gorkem.common.data.repository

import com.gorkem.common.data.local.dao.FavouriteProgramDao
import com.gorkem.common.data.local.entity.FavouriteProgramEntity
import com.gorkem.common.data.local.entity.mapToUIModel
import com.gorkem.common.ui.ProgramUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FavouriteProgramRepository {
    fun getFavouriteList(): Flow<List<ProgramUIModel>>
    fun insert(entity: FavouriteProgramEntity)
    fun delete(id: String)
}

class FavouriteProgramRepositoryImpl(private val favouriteProgramDao: FavouriteProgramDao) :
    FavouriteProgramRepository {
    override fun getFavouriteList(): Flow<List<ProgramUIModel>> =
        favouriteProgramDao.getFavouriteList().map { list -> list.map { it.mapToUIModel() } }

    override fun insert(entity: FavouriteProgramEntity) = favouriteProgramDao.insert(entity)

    override fun delete(id: String) = favouriteProgramDao.delete(id)
}
