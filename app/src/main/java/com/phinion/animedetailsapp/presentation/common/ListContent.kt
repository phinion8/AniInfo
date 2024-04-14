package com.phinion.animedetailsapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.phinion.animedetailsapp.R
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.navigation.Screen
import com.phinion.animedetailsapp.presentation.components.RatingWidget
import com.phinion.animedetailsapp.presentation.components.ShimmerEffect
import com.phinion.animedetailsapp.ui.theme.*
import com.phinion.animedetailsapp.utils.Constants.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>, navController: NavController,
    inSearchScreen: Boolean
) {


    val result = handlePagingResult(heroes = heroes, inSearchScreen = inSearchScreen)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = heroes,
                key = { hero ->
                    hero.id
                }
            ) { hero ->
                hero?.let {
                    HeroItem(navController = navController, hero = it)
                }

            }
        }
    }

}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>,
    inSearchScreen: Boolean? = null
): Boolean {
    heroes.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                if (inSearchScreen == false){
                    ShimmerEffect()
                }else{
                    EmptyScreen()
                }
                false
            }
            error != null -> {
                EmptyScreen(error = error, heroes = heroes)
                false
            }
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@Composable
fun HeroItem(
    navController: NavController, hero: Hero
) {

    val painter = rememberAsyncImagePainter(
        model = "$BASE_URL${hero.image}", placeholder = painterResource(
            id = R.drawable.ic_placeholder
        ), error = painterResource(id = R.drawable.ic_placeholder)
    )
    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable { navController.navigate(Screen.Details.passHeroId(heroId = hero.id)) },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING, bottomEnd = LARGE_PADDING
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {

                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING), rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )

                }

            }


        }
    }

}

@Composable
@Preview
fun HeroItemPreview() {

    HeroItem(
        navController = rememberNavController(), hero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Some random text",
            rating = 4.5,
            power = 100,
            month = "",
            day = "",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList()
        )
    )

}