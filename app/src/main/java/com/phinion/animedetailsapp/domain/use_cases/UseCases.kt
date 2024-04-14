package com.phinion.animedetailsapp.domain.use_cases

import com.phinion.animedetailsapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.phinion.animedetailsapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.phinion.animedetailsapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.phinion.animedetailsapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.phinion.animedetailsapp.domain.use_cases.search_heroes.SearchHeroesUseCase

//Use case is the simple interaction between the user and the application
//For saving the on boarding state is one use case and reading that is another use case
data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroesUseCase: SearchHeroesUseCase,
    val getSelectedHeroUseCase: GetSelectedHeroUseCase
)
