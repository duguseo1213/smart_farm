package com.wcd.farm.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wcd.farm.presentation.view.login.LoginScreen
import com.wcd.farm.presentation.view.main.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("main_screen") {
            MainScreen()
        }
    }
}