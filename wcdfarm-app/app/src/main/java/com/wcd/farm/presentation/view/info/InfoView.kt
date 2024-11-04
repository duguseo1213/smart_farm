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

@Composable
fun InfoScreen() {
    Column(Modifier.fillMaxHeight()) {
        Spacer(modifier = Modifier.weight(0.05f))
        CrtStateView(Modifier.weight(0.2f).clip(RoundedCornerShape(16.dp)).shadow(4.dp, RoundedCornerShape(16.dp))) // 0.25
        Spacer(modifier = Modifier.weight(0.05f))

        SelectCropsView(Modifier.weight(0.075f).clip(RoundedCornerShape(16.dp))) // 0.1f
        Spacer(modifier = Modifier.weight(0.025f))

        GrowthGraphView(Modifier.weight(0.4f).clip(RoundedCornerShape(16.dp))) // 0.4f
        Spacer(modifier = Modifier.weight(0.05f))

        Recommend(Modifier.weight(0.2f).clip(RoundedCornerShape(16.dp))) // 0.25f
        Spacer(modifier = Modifier.weight(0.025f))
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFECEDC1, widthDp = 360, heightDp = 640)
@Composable
fun PreviewInfoScreen() {
    InfoScreen()
}