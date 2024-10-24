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

@Composable
fun GalleryView() {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val calendarState by viewModel.collectAsState(MemorialViewState::showDialog)
    val selectedDate by viewModel.selectedDate.collectAsState()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Text("${selectedDate.year}", fontSize = 24.sp, color = Color.White)
                Text(
                    "${selectedDate.month.name[0]}${
                        selectedDate.month.name.substring(1).lowercase()
                    } ${selectedDate.dayOfMonth}", fontSize = 32.sp, color = Color.White
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
                    tint = Color.White
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
    val memoryList = listOf(1, 2, 3)
    val listState = rememberLazyListState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(memoryList) { memory ->
            MemoryView()
        }
    }
}

@Composable
fun MemoryView() {
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
            Text("12:00 잘 자라라$$")
            Icon(imageVector = Icons.Outlined.EditCalendar, contentDescription = "Edit")
        }
        Button(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.farm),
                contentDescription = "Farm",
                modifier = Modifier.clip(
                    RoundedCornerShape(16.dp)
                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarModal(onDismissRequest: () -> Unit) {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val crtYear = LocalDate.now().year

// TODO demo how to read the selected date from the state.

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