package com.phinion.animedetailsapp.domain.repository

import com.phinion.animedetailsapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}