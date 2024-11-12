package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.wcd.farm.R
import com.wcd.farm.presentation.intent.DiseaseViewIntent
import com.wcd.farm.presentation.intent.HomeViewIntent
import com.wcd.farm.presentation.state.HomeViewState
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font


val customFontFamily_main1 = FontFamily(Font(R.font.juri))

@Composable
fun MenuContainer(modifier: Modifier) {
    val viewModel: HomeViewModel = mavericksViewModel()
    val diseaseViewModel: DiseaseViewModel = mavericksViewModel()
    val isUserOnFarm by viewModel.collectAsState(HomeViewState::isUserOnFarm)
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (!isUserOnFarm) {
            MenuButton(R.drawable.watering_btn, "물주기") { viewModel.requestWatering() }
            MenuButton(R.drawable.camera_btn, "사진 촬영") { viewModel.requestFilm() }
            MenuButton(R.drawable.disease_btn, "질병 확인") { diseaseViewModel.sendIntent(DiseaseViewIntent.ShowDiseaseView) }
        } else {
            MenuLongButton(icon = Icons.Outlined.WaterDrop, description = "물주기") { viewModel.requestWatering() }
        }
    }
}

@Composable
fun MenuButton(imageRes: Int, description: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.rsBlurShadow(4.dp, color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp))
    ) {
        Box(
            modifier = Modifier
                .height(90.dp)
                .width(100.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.menu_background),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = description,
                    modifier = Modifier
                        .height(45.dp)
                        .width(55.dp)
                )
                Text(description,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = customFontFamily_main1,
                    color = Color(0xFF4B4747)
                )
            }
        }
    }
}

@Composable
fun MenuLongButton(icon: ImageVector, description: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.menu_background),
                contentDescription = "background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Water",
                    modifier = Modifier.size(36.dp)
                )
                Text(description, modifier = Modifier.padding(4.dp))
            }
        }
    }
}