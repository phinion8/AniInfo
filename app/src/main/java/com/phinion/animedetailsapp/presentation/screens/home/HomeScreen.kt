package com.phinion.animedetailsapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.animedetailsapp.navigation.Screen
import com.phinion.animedetailsapp.presentation.common.ListContent
import com.phinion.animedetailsapp.presentation.components.RatingWidget
import com.phinion.animedetailsapp.ui.theme.LARGE_PADDING
import com.phinion.animedetailsapp.ui.theme.statusBarColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    //collect as lazy paging items function will collect the data as a flow from paging data
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)

                }
            )
        },
        content = {
            ListContent(heroes = allHeroes, navController = navController, inSearchScreen = false)
        }
    )

}