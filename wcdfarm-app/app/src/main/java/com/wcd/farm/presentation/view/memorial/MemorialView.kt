package com.wcd.farm.presentation.view.memorial

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.wcd.farm.R
import com.wcd.farm.data.model.HarmDTO
import com.wcd.farm.presentation.intent.MemorialViewIntent
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.MemorialViewModel
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


const val GALLERY_VIEW = 1
const val GROWTH_VIEW = 2
const val ANIMAL_VIEW = 3
const val THEFT_VIEW = 4

@Composable
fun MemorialScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val showView by viewModel.collectAsState(MemorialViewState::showMemorialView)
    val showInvasionVideo by viewModel.collectAsState(MemorialViewState::showInvasionVideo)
    val gardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()

    LaunchedEffect(crtGarden) {
        //crtGarden?.let { viewModel.getTimeLapse(gardenList[it].gardenId) }
    }

    Spacer(modifier = Modifier.fillMaxHeight(0.07f))
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            MemorialMenu(showView == GALLERY_VIEW, "갤러리", R.drawable.gallery_icon) {
                viewModel.sendIntent(MemorialViewIntent.ShowGalleryView)
            }
            MemorialMenu(showView == GROWTH_VIEW, "성장과정", R.drawable.growth_icon) {
                viewModel.sendIntent(MemorialViewIntent.ShowGrowthView)
            }
            MemorialMenu(showView == ANIMAL_VIEW, "방문동물", R.drawable.animal_icon) {
                viewModel.sendIntent(MemorialViewIntent.ShowAnimalView)
            }
            MemorialMenu(showView == THEFT_VIEW, "도난관리", R.drawable.theft_icon) {
                viewModel.sendIntent(MemorialViewIntent.ShowTheftView)
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        when (showView) {
            GALLERY_VIEW -> GalleryView()
            GROWTH_VIEW -> TimeLapseScreen()
            ANIMAL_VIEW -> AnimalScreen()
            THEFT_VIEW -> TheftScreen()
        }
    }

    if (showInvasionVideo) {
        InvasionVideoView()
    }

}


@Composable
fun MemorialMenu(crtMenu: Boolean, menuTitle: String, iconResId: Int, onClick: () -> Unit) {
    val color =
        ButtonDefaults.buttonColors(
            containerColor = Color(0XFF2F6348),
            contentColor = Color(0XFFECEDC1)
        )
    val color2 =
        ButtonDefaults.buttonColors(
            containerColor = Color(0XFF1A874D),
            contentColor = Color(0XFFECEDC1)
        )

    Button(
        onClick = onClick,
        colors = if (crtMenu) color else color2,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .size(75.dp, 77.dp)
            .rsBlurShadow(
                4.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(0.25f),
                offset = DpOffset(x = 0.dp, y = 4.dp)
            )

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = menuTitle,
                modifier = Modifier.size(40.dp)
            )
            Text(menuTitle)
        }
    }
}

@Composable
fun EmptyListView(notify: String) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val height = maxHeight
        Text(
            notify,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.offset(y = -maxHeight / 5)
        )
    }
}

@Composable
fun InvasionView(harm: HarmDTO, harmType: String) {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val date = ZonedDateTime.parse(harm.createdDate)

    val invasionDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val invasionTime = date.format(DateTimeFormatter.ofPattern("a hh:mm"))
    
    Column {
        Text(
            invasionDate,
            color = Color(0xFF204833),
            fontSize = 28.sp,
            modifier = Modifier.padding(8.dp, 24.dp),
            fontFamily = customFontFamily1
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "$invasionTime ${harm.harmTarget} $harmType",
                    fontSize = 20.sp,
                    fontFamily = customFontFamily3
                )
                Button(
                    onClick = { /*TODO*/ },
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(0.dp)
                        .size(30.dp),
                    colors = buttonTransparentTheme(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.Outlined.DeleteOutline,
                        contentDescription = "Delete",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            TextButton(
                onClick = {
                    viewModel.selectHarm(harm)
                    viewModel.sendIntent(MemorialViewIntent.ShowInvasionView)
                },
                modifier = Modifier.fillMaxSize(),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                AsyncImage(
                    model = harm.harmPicture,
                    contentDescription = "harm",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Composable
fun InvasionVideoView() {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val selectedHarm by viewModel.selectedHarm.collectAsState()
    val videoUrl by viewModel.invasionVideoUrl.collectAsState()

    val invasionDate = selectedHarm?.createdDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val invasionTime = selectedHarm?.createdDate?.format(DateTimeFormatter.ofPattern("a hh:mm"))

    AlertDialog(
        onDismissRequest = { viewModel.sendIntent(MemorialViewIntent.HideInvasionView) },
        confirmButton = { /*TODO*/ },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "$invasionTime ${selectedHarm?.harmTarget} ${selectedHarm?.harmTarget}",
                    fontSize = 20.sp,
                    fontFamily = customFontFamily3
                )
                Button(
                    onClick = { /*TODO*/ },
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(0.dp)
                        .size(30.dp),
                    colors = buttonTransparentTheme(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.Outlined.DeleteOutline,
                        contentDescription = "Delete",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            VideoPlayer(videoUrl = videoUrl)
        })
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val exoPlayer = ExoPlayer.Builder(LocalContext.current).build()

    val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.prepare()
    exoPlayer.play()

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Preview
@Composable
fun PreviewInvasionView() {
    MemorialScreen()
}