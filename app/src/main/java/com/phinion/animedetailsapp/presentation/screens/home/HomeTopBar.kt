package com.phinion.animedetailsapp.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.phinion.animedetailsapp.R
import com.phinion.animedetailsapp.ui.theme.topAppBackgroundColor
import com.phinion.animedetailsapp.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(
    onSearchClicked: () -> Unit
) {

    TopAppBar(
        title = {
            Text(text = "Explore", color = MaterialTheme.colors.topAppBarContentColor)
        },
        backgroundColor = MaterialTheme.colors.topAppBackgroundColor,
        actions = {
            IconButton(onClick = { onSearchClicked() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))
            }
        }
    )

}

