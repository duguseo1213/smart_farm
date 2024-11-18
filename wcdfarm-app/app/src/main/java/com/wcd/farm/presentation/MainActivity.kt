package com.wcd.farm.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.viewModel
import com.google.firebase.FirebaseApp
import com.wcd.farm.data.remote.ServerClient
import com.wcd.farm.presentation.intent.AppViewIntent
import com.wcd.farm.presentation.view.AppNavigation
import com.wcd.farm.presentation.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mavericks.initialize(this)
        ServerClient.init(applicationContext)
        FirebaseApp.initializeApp(applicationContext)
        val appViewModel: AppViewModel by viewModel()
        this.appViewModel = appViewModel

        val intent = intent
        handleIntent(intent)

        setContent {
            AppNavigation()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            val accessToken = uri.getQueryParameter("accessToken")
            val refreshToken = uri.getQueryParameter("refreshToken")

            if (accessToken != null && refreshToken != null) {
                appViewModel.setAccessToken(accessToken)
                appViewModel.setRefreshToken(refreshToken)
                appViewModel.setLoginState(true)
                appViewModel.sendIntent(AppViewIntent.ShowMainView)
            }
        }
    }
}