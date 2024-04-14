package com.phinion.animedetailsapp.data.repository

import com.phinion.animedetailsapp.data.local.BorutoDatabase
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase): LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}