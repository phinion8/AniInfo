package com.phinion.animedetailsapp.presentation.screens.details

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.animedetailsapp.R
import com.phinion.animedetailsapp.domain.model.Hero
import com.phinion.animedetailsapp.presentation.components.InfoBox
import com.phinion.animedetailsapp.presentation.components.OrderedList
import com.phinion.animedetailsapp.ui.theme.*
import com.phinion.animedetailsapp.utils.Constants.ABOUT_TEXT_MAX_LINES
import com.phinion.animedetailsapp.utils.Constants.BASE_URL
import com.phinion.animedetailsapp.utils.Constants.MINIMUM_BACKGROUND_IMAGE

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navController: NavController,
    selectedHero: Hero?,
    colors: Map<String, String>
) {

    var vibrant by remember {
        mutableStateOf("#000000")
    }

    var darkVibrant by remember {
        mutableStateOf("#000000")
    }

    var onDarkVibrant by remember {
        mutableStateOf("#FFFFFF")
    }

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(parseColor(darkVibrant))
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING else EXPANDED_RADIUS_LEVEL
    )


    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = radiusAnim, topEnd = radiusAnim),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(Color(parseColor(darkVibrant)))
            ) {
                selectedHero?.let {
                    BottomSheetContent(
                        selectedHero = it,
                        infoBoxIconColor = Color(parseColor(vibrant)),
                        sheetBackgroundColor = Color(parseColor(darkVibrant)),
                        contentColor = Color(parseColor(onDarkVibrant))
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                if (selectedHero != null) {
                    BackgroundContent(
                        heroImage = selectedHero.image,
                        backgroundColor = Color(parseColor(darkVibrant)),
                        onClosedClick = {
                            navController.popBackStack()
                        },
                        imageFraction = currentSheetFraction
                    )
                }
            }
        })

}


@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {

    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = com.phinion.animedetailsapp.R.drawable.ic_logo),
                contentDescription = "logo icon",
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )


        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )

        }

    }

}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClosedClick: () -> Unit
) {

    val imageUrl = "$BASE_URL$heroImage"
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(id = R.drawable.ic_placeholder),
        placeholder = painterResource(id = R.drawable.ic_placeholder)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MINIMUM_BACKGROUND_IMAGE)
                .align(
                    Alignment.TopStart
                ),
            painter = painter,
            contentDescription = "hero image",
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(onClick = { onClosedClick() }) {

                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close, contentDescription = "back button",
                    tint = Color.White
                )

            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction

        }
    }