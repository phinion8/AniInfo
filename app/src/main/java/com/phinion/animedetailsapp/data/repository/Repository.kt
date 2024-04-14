package com.phinion.animedetailsapp.data.repository

import androidx.paging.PagingData
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.domain.repository.DataStoreOperations
import com.phinion.animedetailsapp.domain.repository.LocalDataSource
import com.phinion.animedetailsapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//here we are injecting the data store in the repository so when the repository is called the dagger hilt will
//start looking in the modules that which function have the return of the data store operations
class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllHeroes(): Flow<PagingData<Hero>>{
        return remote.getAllHeroes()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>>{
        return remote.searchHeroes(query = query)
    }

    suspend fun getSelectedHero(heroId: Int): Hero{
        return local.getSelectedHero(heroId = heroId)
    }

    suspend fun saveOnBoardingState(completed: Boolean){
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean>{
        return dataStore.readOnBoardingState()
    }
}