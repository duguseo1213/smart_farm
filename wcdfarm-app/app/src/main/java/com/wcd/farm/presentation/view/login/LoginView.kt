package com.wcd.farm.presentation.view.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wcd.farm.R
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(0.25f))
        LogoImage()
        Spacer(modifier = Modifier.weight(0.25f))
        KakaoLoginButton {
            val url = "https://k11c104.p.ssafy.io/oauth2/authorization/kakao?redirect_uri=https://k11c104.p.ssafy.io/app"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Main) {
                    context.startActivity(intent)
                }
            }

            /*viewModel.login(context) {
                navController.navigate("main_screen") {
                    popUpTo("login_screen") {
                        inclusive = true
                    }
                }
            }*/
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun LogoImage() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun KakaoLoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.kakao_login_icon),
            contentDescription = "Button",
            contentScale = ContentScale.FillWidth
        )
    }
}