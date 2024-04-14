package com.phinion.animedetailsapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val StarColor = Color(0xFFFFC94D)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val Colors.statusBarColor
@Composable
get() = if (isLight) Purple700 else Color.Black

val Colors.welcomeScreenBackgroundColor
@Composable
get() = if (isLight) Color.White else Color.Black

val Colors.titleColor
@Composable
get() = if (isLight) Color.DarkGray else Color.LightGray

val Colors.descriptionColor
@Composable
get() = if (isLight) Color.DarkGray.copy(alpha = 0.5f) else Color.LightGray.copy(0.5f)

val Colors.activeIndicatorColor
@Composable
get() = if (isLight) Purple500 else Purple700

val Colors.inactiveIndicatorColor
@Composable
get() = if (isLight) Color.LightGray else Color.DarkGray

val Colors.buttonBackgroundColor
@Composable
get() = if (isLight) Purple500 else Purple700

val Colors.topAppBarContentColor: Color
@Composable
get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBackgroundColor: Color
@Composable
get() = if (isLight) Purple500 else Color.Black