package com.wcd.farm.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.airbnb.mvrx.Mavericks
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.wcd.farm.presentation.view.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, "b4a372481110807e07d845885b9da318")
        Mavericks.initialize(this)
        /*val keyHash = Utility.getKeyHash(this)
        Log.e("TEST", "keyHash: $keyHash")*/

        setContent {
            AppNavigation()
        }
    }
}