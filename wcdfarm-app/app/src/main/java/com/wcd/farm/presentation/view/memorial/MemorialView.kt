package com.wcd.farm.presentation.view.memorial

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.wcd.farm.R
import com.wcd.farm.presentation.intent.MemorialViewIntent
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.MemorialViewModel



const val GALLERY_VIEW = 1
const val GROWTH_VIEW = 2
const val ANIMAL_VIEW = 3
const val THEFT_VIEW = 4

@Composable
fun MemorialScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val showView by viewModel.collectAsState(MemorialViewState::showMemorialView)

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
            GROWTH_VIEW -> Text("Growth")
            ANIMAL_VIEW -> AnimalScreen()
            THEFT_VIEW -> TheftScreen()
        }
    }
}


@Composable
fun MemorialMenu(crtMenu: Boolean, menuTitle: String, iconResId: Int, onClick: () -> Unit) {
    val color =
        ButtonDefaults.buttonColors(containerColor = Color(0XFF2F6348), contentColor = Color(0XFFECEDC1))
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
fun InvasionView() {
    Column {
        Text(
            "2024-09-17",
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
                Text("오후 03:30 강아지 방문", fontSize = 20.sp, fontFamily = customFontFamily3)
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
            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                colors = buttonTransparentTheme(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dog_on_farm),
                    contentDescription = "invader",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }


}

@Preview
@Composable
fun PreviewInvasionView() {
    MemorialScreen()
}