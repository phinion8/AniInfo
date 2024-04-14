package com.phinion.animedetailsapp.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.animedetailsapp.navigation.Screen
import com.phinion.animedetailsapp.presentation.common.EmptyScreen
import com.phinion.animedetailsapp.presentation.common.ListContent
import com.phinion.animedetailsapp.ui.theme.statusBarColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {

    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()
    var inSearchScreen by searchViewModel.inSearchScreen

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    inSearchScreen = true

    Scaffold(
        topBar = {
            SearchTopAppBar(text = searchQuery, onTextChange = {
                searchViewModel.updateSearchQuery(query = it)
            }, onSearchClicked = {
                searchViewModel.searchHeroes(query = it)
                inSearchScreen = false

            }, onClosedClicked = {

                navController.popBackStack()


            })
        },
        content = {


            ListContent(heroes = heroes, navController = navController, inSearchScreen = inSearchScreen)

        }
    )
}