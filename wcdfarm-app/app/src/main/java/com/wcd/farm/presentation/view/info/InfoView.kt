package com.wcd.farm.presentation.view.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpOffset
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import androidx.compose.ui.graphics.Color

@Composable
fun InfoScreen() {
    Column(Modifier.fillMaxHeight()) {
        Spacer(modifier = Modifier.weight(0.01f))
        CrtStateView(
            Modifier
                .rsBlurShadow(4.dp, shape = RoundedCornerShape(16.dp), color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp))
                .clip(RoundedCornerShape(16.dp))
                .weight(0.2f)
        )
        Spacer(modifier = Modifier.weight(0.05f))

        SelectCropsView(
            Modifier
                .rsBlurShadow(4.dp, shape = RoundedCornerShape(16.dp), color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp))
                .clip(RoundedCornerShape(16.dp))
                .weight(0.075f)
        )
        Spacer(modifier = Modifier.weight(0.025f))

        GrowthGraphView(
            Modifier
                .rsBlurShadow(4.dp, shape = RoundedCornerShape(16.dp), color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp))
                .clip(RoundedCornerShape(16.dp))
                .weight(0.4f)
                ) // 0.4f
        Spacer(modifier = Modifier.weight(0.05f))

        Recommend(Modifier
            .rsBlurShadow(4.dp, shape = RoundedCornerShape(16.dp), color = Color.Black.copy(0.25f), offset = DpOffset(x = 0.dp, y = 4.dp))
            .weight(0.2f)
            .clip(RoundedCornerShape(16.dp))) // 0.25f
        Spacer(modifier = Modifier.weight(0.025f))
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFECEDC1, widthDp = 360, heightDp = 640)
@Composable
fun PreviewInfoScreen() {
    InfoScreen()
}