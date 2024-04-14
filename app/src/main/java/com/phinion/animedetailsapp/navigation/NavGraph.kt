package com.phinion.animedetailsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.phinion.animedetailsapp.presentation.screens.details.DetailScreen
import com.phinion.animedetailsapp.presentation.screens.details.DetailsViewModel
import com.phinion.animedetailsapp.presentation.screens.home.HomeScreen
import com.phinion.animedetailsapp.presentation.screens.home.HomeViewModel
import com.phinion.animedetailsapp.presentation.screens.search.SearchScreen
import com.phinion.animedetailsapp.presentation.screens.search.SearchViewModel
import com.phinion.animedetailsapp.presentation.screens.splash.SplashScreen
import com.phinion.animedetailsapp.presentation.screens.splash.SplashViewModel
import com.phinion.animedetailsapp.presentation.screens.welcome.WelcomeScreen
import com.phinion.animedetailsapp.presentation.screens.welcome.WelcomeViewModel
import com.phinion.animedetailsapp.utils.Constants.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    splashViewModel: SplashViewModel,
    welcomeViewModel: WelcomeViewModel,
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel
) {

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(route = Screen.Splash.route) {

            SplashScreen(navController = navController, splashViewModel = splashViewModel)

        }

        composable(route = Screen.Welcome.route) {

            WelcomeScreen(navController = navController, welcomeViewModel = welcomeViewModel)

        }

        composable(route = Screen.Home.route) {

            HomeScreen(homeViewModel = homeViewModel, navController = navController)

        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(navController = navController)
        }

        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController, searchViewModel = searchViewModel)
        }

    }

}