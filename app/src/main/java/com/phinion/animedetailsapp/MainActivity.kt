package com.phinion.animedetailsapp

import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.phinion.animedetailsapp.navigation.SetUpNavGraph
import com.phinion.animedetailsapp.presentation.screens.details.DetailsViewModel
import com.phinion.animedetailsapp.presentation.screens.home.HomeViewModel
import com.phinion.animedetailsapp.presentation.screens.search.SearchViewModel
import com.phinion.animedetailsapp.presentation.screens.splash.SplashViewModel
import com.phinion.animedetailsapp.presentation.screens.welcome.WelcomeViewModel
import com.phinion.animedetailsapp.ui.theme.AnimeDetailsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val splashViewModel: SplashViewModel by viewModels()
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeDetailsAppTheme {

                navController = rememberNavController()
                SetUpNavGraph(
                    navController = navController,
                    splashViewModel = splashViewModel,
                    welcomeViewModel = welcomeViewModel,
                    homeViewModel = homeViewModel,
                    searchViewModel = searchViewModel
                )

            }
        }
    }
}

