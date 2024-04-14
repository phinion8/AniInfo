package com.phinion.animedetailsapp.domain.use_cases.save_onboarding

import com.phinion.animedetailsapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    //invoke means whenever we call the class anywhere then we don not have to call the invoke function explicitly
    suspend operator fun invoke(completed: Boolean){
        repository.saveOnBoardingState(completed = completed)
    }
}