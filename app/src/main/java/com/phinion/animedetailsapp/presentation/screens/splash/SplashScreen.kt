package com.phinion.animedetailsapp.presentation.screens.splash

import android.window.SplashScreen
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.phinion.animedetailsapp.R
import com.phinion.animedetailsapp.navigation.Screen
import com.phinion.animedetailsapp.ui.theme.Purple500
import com.phinion.animedetailsapp.ui.theme.Purple700
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel
) {
    val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState()
    val degrees = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navController.popBackStack()
        if (onBoardingCompleted){
            navController.navigate(Screen.Home.route)
        }else{
            navController.navigate(Screen.Welcome.route)
        }
    }
    Splash(degrees = degrees.value)
}

@Composable
fun Splash(degrees: Float) {

    if(isSystemInDarkTheme()){

        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo), contentDescription = stringResource(
                    R.string.app_logo
                ), modifier = Modifier.rotate(degrees = degrees)
            )
        }


    }else{

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Purple700,
                            Purple500
                        )
                    )
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo), contentDescription = stringResource(
                    R.string.app_logo
                ), modifier = Modifier.rotate(degrees = degrees)
            )
        }

    }



}