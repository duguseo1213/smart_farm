package com.wcd.farm.presentation.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.DataExploration
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.wcd.farm.R
import com.wcd.farm.data.remote.ServerClient
import com.wcd.farm.presentation.intent.DiseaseViewIntent
import com.wcd.farm.presentation.state.DiseaseViewState
import com.wcd.farm.presentation.view.home.DiseaseScreen
import com.wcd.farm.presentation.view.memorial.MemorialScreen
import com.wcd.farm.presentation.view.home.HomeScreen
import com.wcd.farm.presentation.view.info.InfoScreen
import com.wcd.farm.presentation.view.mypage.MyPageScreen
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.DiseaseViewModel
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import com.wcd.farm.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val HOME = "Home"
const val INFO = "Info"
const val MEMORIAL = "Memorial"
const val MY_PAGE = "MyPage"

@Composable
fun MainLayout() {
    val currentScreen = remember { mutableStateOf(HOME) }
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val diseaseViewModel: DiseaseViewModel = mavericksViewModel()
    val showDiseaseView by diseaseViewModel.collectAsState(DiseaseViewState::showDiseaseView)
    val homeViewModel: HomeViewModel = mavericksViewModel()

    val crtGarden by homeViewModel.crtGarden.collectAsState()

    LaunchedEffect(Unit) {
        val longitude = 126.8071876
        val latitude = 35.2040949
        FirebaseMessaging.getInstance().token.addOnCompleteListener { token ->
            CoroutineScope(Dispatchers.IO).launch {
                val response = ServerClient.userApi.setFcmToken(token.result)

                if(response.isSuccessful) {
                    Log.e("TEST", response.body().toString())
                } else {
                    Log.e("TEST", response.errorBody()!!.string())
                }
            }
        }
        homeViewModel.getGardenList()

        homeViewModel.getStreamKeys()
        weatherViewModel.getNearForecastWeather(latitude, longitude)
        weatherViewModel.getForecastWeather()

        //homeViewModel
    }

    if(crtGarden != null) {
        Scaffold(
            containerColor = Color(LocalContext.current.getColor(R.color.background)),
            bottomBar = { BottomBar(currentScreen)}
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Box(modifier = Modifier.padding(36.dp, 18.dp)) {
                    when (currentScreen.value) {
                        HOME -> HomeScreen()
                        INFO -> InfoScreen()
                        MEMORIAL -> MemorialScreen()
                        MY_PAGE -> MyPageScreen()
                    }
                }
            }
        }

        if(showDiseaseView) {
            DiseaseScreen {
                diseaseViewModel.sendIntent(DiseaseViewIntent.HideDiseaseView)
            }
        }
    } else {
        //homeViewModel.addGarden(2L)
    }

}

@Composable
fun BottomBar(currentScreen: MutableState<String>) {
    BottomAppBar(
        modifier = Modifier.fillMaxHeight(0.075f),
        containerColor = Color(LocalContext.current.getColor(R.color.bottom_bar)),
        contentColor = Color.White,
        contentPadding = PaddingValues(20.dp, 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(currentScreen, Icons.Outlined.Home, HOME) {
                currentScreen.value = HOME
            }
            BottomBarItem(currentScreen, Icons.Outlined.DataExploration, INFO) {
                currentScreen.value = INFO
            }
            BottomBarItem(currentScreen, Icons.Outlined.Archive, MEMORIAL) {
                currentScreen.value = MEMORIAL
            }
            BottomBarItem(currentScreen, Icons.Outlined.Person, MY_PAGE) {
                currentScreen.value = MY_PAGE
            }
        }
    }
}

@Composable
fun BottomBarItem(currentScreen: MutableState<String>, icon: ImageVector, description: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(16.dp, 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val color = if(currentScreen.value == description) Color.White else Color(0XFF81AF96)
            Icon(imageVector = icon, contentDescription = description, modifier = Modifier.size(28.dp), tint = color)
            Text(description, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = color)
        }
    }
}