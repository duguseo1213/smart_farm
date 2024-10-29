package com.wcd.farm.presentation.view.info

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme

@Composable
fun SelectCropsView(modifier: Modifier) {
    var showCropsList by remember { mutableStateOf(false) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CropsSelectButton{ showCropsList = true }

        CropsListView(showCropsList) {
            showCropsList = false
        }
    }
}

@Composable
fun CropsSelectButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = buttonTransparentTheme(),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFFFFCEB)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "상추",
                color = Color.Black,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
            Image(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "DropDown",
                modifier = Modifier.padding(16.dp, 0.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

@Composable
fun CropsListView(showCropsList: Boolean, onDismissRequest: () -> Unit) {
    val paddingValue = 36.dp
    val paddingPx = with(LocalDensity.current) { paddingValue.toPx() }

    val calculatedWidth = LocalDensity.current.run {
        (LocalConfiguration.current.screenWidthDp.dp.toPx() - paddingPx * 2).toDp()
    }

    DropdownMenu(
        expanded = showCropsList,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .width(calculatedWidth)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        CropsItemView {
            onDismissRequest()
        }
    }
}

@Composable
fun CropsItemView(onDismissRequest: () -> Unit) {
    DropdownMenuItem(text = { Text("상추") }, onClick = { onDismissRequest() })
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectCropsView() {
    SelectCropsView(modifier = Modifier)
}
