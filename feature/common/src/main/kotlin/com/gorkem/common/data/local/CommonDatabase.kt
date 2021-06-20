package com.gorkem.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorkem.common.data.local.dao.FavouriteProgramDao
import com.gorkem.common.data.local.entity.FavouriteProgramEntity

@Database(entities = [FavouriteProgramEntity::class], version = 1)
abstract class CommonDatabase : RoomDatabase() {
    abstract fun favouriteProgramDao(): FavouriteProgramDao
}
