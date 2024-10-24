package com.wcd.farm.presentation.view.memorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.intent.MemorialViewIntent
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.viewmodel.MemorialViewModel

const val GALLERY_VIEW = 1
const val GROWTH_VIEW = 2
const val ANIMAL_VIEW = 3
const val THEFT_VIEW = 4

@Composable
fun MemorialScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val showView by viewModel.collectAsState(MemorialViewState::showMemorialView)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MemorialMenu(showView == GALLERY_VIEW) {
                viewModel.sendIntent(MemorialViewIntent.ShowGalleryView)
            }
            MemorialMenu(showView == GROWTH_VIEW) {
                viewModel.sendIntent(MemorialViewIntent.ShowGrowthView)
            }
            MemorialMenu(showView == ANIMAL_VIEW) {
                viewModel.sendIntent(MemorialViewIntent.ShowAnimalView)
            }
            MemorialMenu(showView == THEFT_VIEW) {
                viewModel.sendIntent(MemorialViewIntent.ShowTheftView)
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        when(showView) {
            GALLERY_VIEW -> GalleryView()
            GROWTH_VIEW -> Text("Growth")
            ANIMAL_VIEW -> Text("Animal")
            THEFT_VIEW -> Text("Theft")
        }
    }
}


@Composable
fun MemorialMenu(crtMenu: Boolean, onClick: () -> Unit) {
    val color =
        ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Green)
    val color2 =
        ButtonDefaults.buttonColors(containerColor = Color(0XFF197142), contentColor = Color.White)

    Button(
        onClick = onClick,
        colors = if(crtMenu) color else color2,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .shadow(1.dp, RoundedCornerShape(16.dp), clip = true)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.CollectionsBookmark,
                contentDescription = "Gallery"
            )
            Text("갤러리")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0XFF0D5D32)
@Composable
fun PreviewMemorialScreen() {
    MemorialScreen()
}