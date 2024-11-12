package com.wcd.farm.presentation.view.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.wcd.farm.R


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StateView(modifier: Modifier) {
    TextButton(
        onClick = { Log.e("TEST", "Click") },
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.rsBlurShadow(4.dp, color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp)) // 쉼표 추가
    ){
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                //.clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(24.dp, 4.dp, 24.dp, 24.dp)
        ) {

            Spacer(modifier = Modifier.width(10.dp))
            StateBar(icon = Icons.Outlined.WaterDrop, 90, Color(0xFF86C6C6))
            Spacer(modifier = Modifier.width(24.dp))
            StateBar(icon = Icons.Outlined.WbSunny, ratio = 40, Color(0xFFFFD000))


            var isPlaying by remember { mutableStateOf(false) }
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.happy_farmcharacter))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                isPlaying = isPlaying,
                iterations = 1 // 한 번만 실행
            )

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(200.dp)
                    .clickable() { isPlaying = !isPlaying }
                    //.clickable(indication = null) { isPlaying = !isPlaying }
            )

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(200.dp)
                    .clickable { isPlaying = !isPlaying }
            )

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(200.dp)
                    .clickable { isPlaying = !isPlaying }
            )
            
            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }

}

@Composable
fun StateBar(icon: ImageVector, ratio: Int, color: Color) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Water",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        var height by remember {
            mutableFloatStateOf(0f)
        }
        var textHeight by remember {
            mutableIntStateOf(0)
        }
        Box(
            modifier = Modifier,
            contentAlignment = if (height > textHeight-30) Alignment.TopCenter else Alignment.BottomCenter
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxHeight(ratio.toFloat() / 100)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .width(48.dp)
                    .background(color)
            ) {
                height = maxHeight.value
            }
            Text(
                "$ratio%", modifier = Modifier
                    .padding(4.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        textHeight = layoutCoordinates.size.height
                    },
                color = if (height > textHeight-30) Color.White else Color.Red
            )
        }

    }
}