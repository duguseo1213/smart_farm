package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.viewmodel.HomeViewModel


@Composable
fun MyFarmView(modifier: Modifier) {
    val viewModel: HomeViewModel = mavericksViewModel()
    val farmDisplayType by viewModel.collectAsState(HomeViewState::displayType)
    val interactionSource = remember {
        MutableInteractionSource()
    }
    BoxWithConstraints(modifier = modifier.noRippleClickable {
        when (farmDisplayType) {
            HomeViewState.IMAGE -> viewModel.sendIntent(HomeViewIntent.ShowFarmLive)
            HomeViewState.LIVE -> viewModel.sendIntent(HomeViewIntent.ShowFarmImage)
        }
    }) {
        val viewPortHeight = maxHeight
        val viewPortWidth = maxWidth

        when (farmDisplayType) {
            HomeViewState.IMAGE -> {
                Image(
                    painter = painterResource(id = R.drawable.dog_on_farm),
                    contentDescription = "",
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth(0.8f)
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.FillBounds
                )
            }

            HomeViewState.LIVE -> {
                VLCPlayer(
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth(0.8f)
                        .align(Alignment.BottomCenter), videoUrl = rtmpURL, subtitleUrl = null
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.house_frame),
            contentDescription = "frame",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .offset(x = maxWidth * 0.75f, y = maxHeight * 0.71f)
                .noRippleClickable {  }
        ) {
            Text(
                "효린이네",
                color = Color.Black,
                letterSpacing = (-2).sp,
                fontFamily = FontFamily(Font(R.font.hakgyo)),
                fontSize = 17.sp,
                modifier = Modifier.width(viewPortWidth * 0.25f)
            )
        }
    }

}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}