package com.phinion.animedetailsapp.domain.use_cases.get_selected_hero

import com.phinion.animedetailsapp.data.repository.Repository
import com.phinion.animedetailsapp.domain.model.Hero

class GetSelectedHeroUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Hero{
        return repository.getSelectedHero(heroId)
    }
}