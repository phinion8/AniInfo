package com.phinion.animedetailsapp.presentation.screens.details

import android.util.Log
import androidx.compose.animation.expandVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.phinion.animedetailsapp.utils.Constants.BASE_URL
import com.phinion.animedetailsapp.utils.PaletteGenerator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedHero by detailsViewModel.selectedHero.collectAsState()

    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPalette()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = PaletteGenerator.convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailsViewModel.setColorPalette(
                            colors = PaletteGenerator.extractColorFromBitmap(bitmap = bitmap)
                        )
                    }
                }
            }
        }
    }


}