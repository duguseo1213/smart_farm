package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.wcd.farm.R


@Composable
fun MyFarmView(modifier: Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val viewPortHeight = maxHeight
        val viewPortWidth = maxWidth

        VLCPlayer(modifier = Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth(0.8f)
            .align(Alignment.BottomCenter), videoUrl = rtmpURL, subtitleUrl = null)
        /*Image(
            painter = painterResource(id = R.drawable.dog_on_farm),
            contentDescription = "",
            alignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.8f)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillBounds
        )*/
        Image(
            painter = painterResource(id = R.drawable.house_frame),
            contentDescription = "frame",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier

                .offset(x = maxWidth * 0.75f, y = maxHeight * 0.71f)
        ) {
            Text(
                "효린이네",
                color = Color.Black,
                letterSpacing = (-2).sp,
                fontFamily = FontFamily(Font(R.font.hakgyo)),
                fontSize = 17.sp,
                modifier = Modifier.width(viewPortWidth * 0.25f)
            )
        }
    }
}