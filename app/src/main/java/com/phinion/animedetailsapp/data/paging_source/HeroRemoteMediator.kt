package com.phinion.animedetailsapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.phinion.animedetailsapp.data.local.BorutoDatabase
import com.phinion.animedetailsapp.data.remote.BorutoApi
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

//Remote mediator combines local storage to remote queries to provide a consistent flow to the users
//In other words we can able to cache data local very easily
@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    //this function is run before when loading is performed
    //checking whether the cached data is out of the date and decide whether to trigger a remote refresh
    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cachedTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cachedTimeout){
            InitializeAction.SKIP_INITIAL_REFRESH
        }else{
            //when the timer is greater than the cached time out then only we want to trigger initial refresh
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    //load function is responsible to updating and backing data set
    override suspend fun load(
        loadType: LoadType,
        //Contains information about the pages loaded so far
        //The most recently accessed index
        //PagingConfig object that you used to initialize the paging stream
        state: PagingState<Int, Hero>
    ): RemoteMediator.MediatorResult {

        return try {
            val page = when (loadType) {
                //paging data content being refreshed which can be result of paging source invalidation,
                //refresh may contain content updates, or initial load
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    //if remote keys of the database is currently empty then in that case we just want to return
                    //the 1 that means load the first page other wise we call the minus function and pass the 1
                    //so that we can reduce the pages by one means we are invalidating the results
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                //load at the start of a paging data
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                //load at the end of the paging data
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?:return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                //with transaction function will allow us to execute multiple database operations, sequentially one by one
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        //clearing all the database table if we are invalidating the data
                        //refresh will mostly call when we open the app for the first time or when we invalidate the data
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    //this will return the list with all the hero remote key object in it with the page requested
                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    //adding all the hero key inside the database table
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                    //adding all the hero objects inside the database table
                    heroDao.addHeroes(heroes = response.heroes)
                }

            }
            //if the response next page is equal to null then we will return true
            //that we have reached the last page and and update the value to true
            //and if that is not the case then we will just return false that there
            //is more data to load
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }


    }

    //Paging state contains the data recently loaded so far
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        //anchor position is the most recently accessed page in the list
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }

    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->

            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)

        }
    }

}