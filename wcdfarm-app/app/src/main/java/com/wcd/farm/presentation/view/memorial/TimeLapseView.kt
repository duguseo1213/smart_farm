package com.wcd.farm.presentation.view.memorial

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.data.model.PictureDTO
import com.wcd.farm.data.model.TimeLapseImageDTO
import com.wcd.farm.presentation.viewmodel.MemorialViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeLapseScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val crtGardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()
    val timeLapseImageList by viewModel.timeLapseList.collectAsState()
    val crtTimeLapseImage by viewModel.crtTimeLapseImage.collectAsState()

    LaunchedEffect(Unit) {
        //crtGarden?.let { viewModel.getTimeLapse(crtGardenList[crtGarden!!].gardenId) }
    }

    LaunchedEffect(timeLapseImageList) {
        viewModel.startTimeLapse()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopTimeLapse()
        }
    }

    crtTimeLapseImage?.let { TimeLapseImage(timeLapseImageList[it]) }
}

@Composable
fun TimeLapseImage(timeLapseImage: TimeLapseImageDTO) {
    //Text(timeLapseImage.image)
    Column {
        val dateTime = ZonedDateTime.parse(timeLapseImage.createdDate)
        val date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        Text(date, fontSize = 22.sp, fontFamily = customFontFamily3)
        AsyncImage(
            model = timeLapseImage.image,
            contentDescription = "Farm",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}