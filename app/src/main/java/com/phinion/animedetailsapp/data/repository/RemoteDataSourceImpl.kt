package com.phinion.animedetailsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.phinion.animedetailsapp.data.local.BorutoDatabase
import com.phinion.animedetailsapp.data.paging_source.HeroRemoteMediator
import com.phinion.animedetailsapp.data.paging_source.SearchHeroesSource
import com.phinion.animedetailsapp.data.remote.BorutoApi
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.domain.repository.RemoteDataSource
import com.phinion.animedetailsapp.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    @OptIn(ExperimentalPagingApi::class)
    //when get all heroes function will be called it will immediately call the pager which will use the local database and get all the hero
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        //this variable will hold all the hero data which we want to fetch and store inside the local database
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        //pager loads the data from the paging source
        return Pager(
            //provides a loading behaviour
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            //remote mediator is used only to load the data from the server and than cache the locally in the room database
            //in that way the local database work as the single source of truth
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                SearchHeroesSource(
                    borutoApi = borutoApi,
                    query = query
                )
            }

        ).flow
    }
}