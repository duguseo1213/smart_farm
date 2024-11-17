package com.wcd.farm.presentation.view.memorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.intent.MemorialViewIntent
import com.wcd.farm.presentation.state.MemorialViewState
import com.wcd.farm.presentation.view.theme.buttonTransparentTheme
import com.wcd.farm.presentation.viewmodel.MemorialViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.wcd.farm.data.model.PictureDTO
import java.sql.Date
import java.time.format.DateTimeFormatter

val customFontFamily1 = FontFamily(
    Font(R.font.bookend_semibold)
)
val customFontFamily2 = FontFamily(
    Font(R.font.ef_yoony)
)
val customFontFamily3 = FontFamily(
    Font(R.font.hakgyo)
)
@Composable
fun GalleryView() {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val calendarState by viewModel.collectAsState(MemorialViewState::showDialog)
    val selectedDate by viewModel.selectedDate.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()

    LaunchedEffect(crtGarden) {
        crtGarden?.let { viewModel.getAllPictures(it.gardenId) }
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Text("${selectedDate.year}",
                    fontSize = 26.sp,
                    color = Color(0xFF204833),
                    fontFamily = customFontFamily1

                )
                Text(
                    "${selectedDate.month.name[0]}${
                        selectedDate.month.name.substring(1).lowercase()
                    } ${selectedDate.dayOfMonth}",
                    fontSize = 36.sp,
                    color = Color(0xFF204833),
                    fontFamily = customFontFamily1,
                )
            }
            Button(
                onClick = { viewModel.sendIntent(MemorialViewIntent.ShowDialog) },
                colors = buttonTransparentTheme(),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = "Calendar",
                    modifier = Modifier
                        .padding(0.dp)
                        .size(36.dp),
                    tint = Color(0xFF204833)
                )
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        MemoryList()

        if (calendarState) {
            CalendarModal {
                viewModel.sendIntent(MemorialViewIntent.HideDialog)
            }
        }
    }

}

@Composable
fun MemoryList() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val picturesList by viewModel.pictureList.collectAsState()
    val listState = rememberLazyListState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(picturesList) { picture ->
            MemoryView(picture)
        }
    }
}

@Composable
fun MemoryView(picture: PictureDTO) {
    Column(
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "${picture.pictureDate.format(DateTimeFormatter.ofPattern("HH:mm"))} ${picture.pictureDescription}",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = customFontFamily3 // customFontFamily1 설정
            )
            Icon(imageVector = Icons.Outlined.EditCalendar,
                contentDescription = "Edit",
                    modifier = Modifier.size(20.dp))
        }
        Button(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            AsyncImage(
                model = picture.pictureUrl,
                contentDescription = "Farm",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarModal(onDismissRequest: () -> Unit) {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val crtYear = LocalDate.now().year
    val datePickerState = rememberDatePickerState(yearRange = IntRange(crtYear - 5, crtYear))

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val date = datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                };
                if (date != null) {
                    viewModel.setSelectedDate(date)
                    onDismissRequest()
                }
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }, content = {
            DatePicker(
                state = datePickerState,
                dateFormatter = DatePickerDefaults.dateFormatter(
                    yearSelectionSkeleton = "MM YYYY",
                    selectedDateSkeleton = "yyyy MM dd E",
                    selectedDateDescriptionSkeleton = "EEEE, MMMM dd"
                )
            )
        })
}