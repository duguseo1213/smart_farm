package com.wcd.farm.presentation.view

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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DataExploration
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.R
import com.wcd.farm.presentation.view.memorial.MemorialScreen
import com.wcd.farm.presentation.view.home.HomeScreen
import com.wcd.farm.presentation.view.info.InfoScreen
import com.wcd.farm.presentation.view.mypage.MyPageScreen
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme

const val HOME = "Home"
const val INFO = "Info"
const val MEMORIAL = "Memorial"
const val MY_PAGE = "MyPage"

@Composable
fun MainLayout() {
    val currentScreen = remember { mutableStateOf(HOME) }

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