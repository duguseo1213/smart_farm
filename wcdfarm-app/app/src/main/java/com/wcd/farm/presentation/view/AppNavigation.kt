package com.wcd.farm.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.state.AppViewState
import com.wcd.farm.presentation.view.login.LoginScreen
import com.wcd.farm.presentation.viewmodel.AppViewModel

@Composable
fun AppNavigation() {
    val appViewModel: AppViewModel = mavericksViewModel()
    val appViewState by appViewModel.collectAsState(AppViewState::appViewState)

    val navController = rememberNavController()
    LaunchedEffect(appViewState) {
        when (appViewState) {
            AppViewState.LOGIN -> navController.navigate(AppViewState.LOGIN)
            AppViewState.MAIN -> navController.navigate(AppViewState.MAIN)
        }
    }

    NavHost(navController = navController, startDestination = AppViewState.LOGIN) {
        composable(AppViewState.LOGIN) {
            LoginScreen()
        }
        composable(AppViewState.MAIN) {
            MainLayout()
        }
    }
}