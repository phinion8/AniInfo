package com.phinion.animedetailsapp.di

import android.content.Context
import com.phinion.animedetailsapp.data.repository.DataStoreOperationsImpl
import com.phinion.animedetailsapp.data.repository.Repository
import com.phinion.animedetailsapp.domain.use_cases.UseCases
import com.phinion.animedetailsapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.phinion.animedetailsapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.phinion.animedetailsapp.domain.repository.DataStoreOperations
import com.phinion.animedetailsapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.phinion.animedetailsapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.phinion.animedetailsapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {

        //whenever we try to inject the data store operations then dagger hilt will initialize the data store operation implemantation
        return DataStoreOperationsImpl(context = context)

    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }

}