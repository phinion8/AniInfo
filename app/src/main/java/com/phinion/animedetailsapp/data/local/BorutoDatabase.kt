package com.phinion.animedetailsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phinion.animedetailsapp.data.local.dao.HeroDao
import com.phinion.animedetailsapp.data.local.dao.HeroRemoteKeysDao
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase: RoomDatabase() {
    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao
}