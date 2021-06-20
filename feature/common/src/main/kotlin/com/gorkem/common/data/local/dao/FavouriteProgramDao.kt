package com.gorkem.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorkem.common.data.local.entity.FavouriteProgramEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteProgramDao {
    @Query("SELECT * FROM favourite_program  order by id DESC")
    fun getFavouriteList(): Flow<List<FavouriteProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteProgramEntity)

    @Query("DELETE FROM favourite_program where id = :id")
    fun delete(id: String)
}
