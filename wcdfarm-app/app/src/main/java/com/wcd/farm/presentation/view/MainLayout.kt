package com.wcd.farm.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.R
import com.wcd.farm.presentation.view.memorial.MemorialScreen
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme

@Composable
fun MainLayout() {

    var currentScreen by remember { mutableStateOf("Home") }

    val Home = "Home"
    val Info = "Info"
    val Record = "Camera"
    val MyPage = "MyPage"

    Scaffold(
        containerColor = Color(LocalContext.current.resources.getColor(R.color.background)),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxHeight(0.075f),
                containerColor = Color.White.copy(alpha = 0.05f),
                contentColor = Color.White,
                contentPadding = PaddingValues(20.dp, 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BottomBarItem(currentScreen, Icons.Outlined.Home, Home) {
                        currentScreen = Home
                    }
                    BottomBarItem(currentScreen, Icons.Default.Face, Info) {
                        currentScreen = Info
                    }
                    BottomBarItem(currentScreen, Icons.Outlined.Build, Record) {
                        currentScreen = Record
                    }
                    BottomBarItem(currentScreen, Icons.Default.Face, MyPage) {
                        currentScreen = MyPage
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {

                Home -> Text("Home")//HomeView(modifier = Modifier.padding(innerPadding))
                Info -> Text("Info")//InfoView(modifier = Modifier.padding(innerPadding))
                Record -> MemorialScreen()//Text("Record")
                MyPage -> Text("MyPage")//MyPageView(modifier = Modifier.padding(innerPadding))

            }

        }

    }
}

@Composable
fun BottomBarItem(currentScreen: String, icon: ImageVector, description: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(24.dp, 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val color = if(currentScreen == description) Color.White else Color(0XFF5C7869)
            Icon(imageVector = icon, contentDescription = description, tint = color/*Color.Black*/)
            Text(description, fontWeight = FontWeight.Medium, fontSize = 12.sp, color = color)
        }
    }

}