package com.phinion.animedetailsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phinion.animedetailsapp.utils.Constants.HERO_REMOTE_KEYS_DATABASE_TABLE

@Entity(tableName = HERO_REMOTE_KEYS_DATABASE_TABLE)
data class HeroRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)
