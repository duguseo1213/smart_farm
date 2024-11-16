package com.wcd.farm.presentation.view.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcd.farm.presentation.viewmodel.InfoViewModel

@Composable
fun Recommend(modifier: Modifier) {
    val viewModel: InfoViewModel = hiltViewModel()
    val recommendCropList by viewModel.recommendCropList.collectAsState()

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFFFFFCEB)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1.0f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Grass,
                contentDescription = "crops",
                modifier = Modifier.size(36.dp)
            )
            Text("추천 조합")
        }

        VerticalDivider(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(0.6f),
            color = Color.Black.copy(alpha = 0.2f)
        )

        LazyColumn(modifier = Modifier.weight(2.0f)) {
            items(recommendCropList.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    rowItems.forEach { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}