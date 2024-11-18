package com.wcd.farm.presentation.view.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CancelPresentation
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.outlined.CancelPresentation
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.viewmodel.HomeViewModel


@Composable
fun MyFarmView(modifier: Modifier, focusManager: FocusManager) {
    val viewModel: HomeViewModel = mavericksViewModel()
    val farmDisplayType by viewModel.collectAsState(HomeViewState::displayType)

    val gardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()
    val gardenStreamKeyMap by viewModel.gardenStreamKeyMap.collectAsState()
    var changeName by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(crtGarden) {
        changeName = gardenList[crtGarden!!].gardenName
    }

    LaunchedEffect(gardenList) {
        if (crtGarden != null) {
            changeName = gardenList[crtGarden!!].gardenName
        }
        viewModel.getStreamKeys()
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
                AsyncImage(
                    model = gardenList[crtGarden!!].gardenImage,
                    //painter = painterResource(id = R.drawable.dog_on_farm),
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
                gardenStreamKeyMap[gardenList[crtGarden!!].gardenId]?.let {
                    VLCPlayer(
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth(0.8f)
                            .align(Alignment.BottomCenter), streamKey = it, subtitleUrl = null
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.house_frame),
            contentDescription = "frame",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .offset(x = maxWidth * 0.785f, y = maxHeight * 0.76f)
        ) {
            BasicTextField(
                value = changeName,
                onValueChange = {
                    if (it.length <= 6) {
                        changeName = it
                    }
                },
                textStyle = TextStyle(
                    letterSpacing = (-2).sp,
                    fontFamily = FontFamily(Font(R.font.hakgyo)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .widthIn(max = viewPortWidth * 0.20f)
                    .focusable()
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                readOnly = !isFocused,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                maxLines = 1
            )

            if (isFocused) {
                Row {
                    Icon(
                        imageVector = Icons.Default.CancelPresentation,
                        contentDescription = "",
                        modifier = Modifier.noRippleClickable {
                            focusManager.clearFocus()
                            changeName = gardenList[crtGarden!!].gardenName
                            isFocused = false
                        })
                    Icon(
                        imageVector = Icons.Default.CheckBox,
                        contentDescription = "",
                        modifier = Modifier.noRippleClickable {
                            focusManager.clearFocus()
                            val prevName = gardenList[crtGarden!!].gardenName
                            crtGarden?.let {
                                viewModel.changeGardenName(gardenList[it].gardenId, changeName)
                                changeName = prevName
                                isFocused = false
                            }
                        })

                }
            }

        }

    }

}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}