package com.phinion.animedetailsapp.presentation.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.phinion.animedetailsapp.ui.theme.TOP_APP_BAR_HEIGHT
import com.phinion.animedetailsapp.ui.theme.topAppBackgroundColor
import com.phinion.animedetailsapp.ui.theme.topAppBarContentColor


@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClosedClicked: () -> Unit
) {

    SearchWidget(
        text = text, onTextChange = onTextChange, onSearchClicked = onSearchClicked,
        onClosedClicked = onClosedClicked
    )

}

@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onClosedClicked: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBackgroundColor
    ) {

        TextField(
            value = text, onValueChange = {
                onTextChange(it)
            }, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search here...",
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colors.topAppBarContentColor),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search Icon",
                    modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onClosedClicked()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.topAppBarContentColor
            )
        )

    }

}

@Composable
@Preview
fun SearchTopAppBarPreview() {

    SearchWidget(text = "", onTextChange = {}, onSearchClicked = {}) {

    }
}

