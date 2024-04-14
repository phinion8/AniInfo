package com.phinion.animedetailsapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.phinion.animedetailsapp.data.repository.Repository
import com.phinion.animedetailsapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Hero>>{
        return repository.getAllHeroes()
    }
}