package com.phinion.animedetailsapp.domain.model

import androidx.annotation.DrawableRes
import com.phinion.animedetailsapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val tittle: String,
    val description: String
) {
    object First : OnBoardingPage(
        R.drawable.greetings,
        tittle = "Greetings",
        description = "Are you a Boruto fan? if you are then we have great news for you."
    )

    object Second : OnBoardingPage(
        R.drawable.explore,
        tittle = "Explore",
        description = "Are you a Boruto fan? if you are then we have great news for you."
    )

    object Third : OnBoardingPage(
        R.drawable.power,
        tittle = "Power",
        description = "Are you a Boruto fan? if you are then we have great news for you."
    )
}
