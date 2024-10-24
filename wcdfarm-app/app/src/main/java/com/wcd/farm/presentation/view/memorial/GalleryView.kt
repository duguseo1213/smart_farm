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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@Composable
fun GalleryView() {
    val viewModel: MemorialViewModel = mavericksViewModel()

    val calendarState by viewModel.collectAsState(MemorialViewState::showDialog)

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Text("2024", fontSize = 24.sp, color = Color.White)
                Text("September 17th", fontSize = 32.sp, color = Color.White)
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
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)


    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val dateFormatter = SimpleDateFormat("yyyy.MM.dd.E", Locale.getDefault())

// TODO demo how to read the selected date from the state.

    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }, content = {
            val formattedDate = dateFormatter.format(
                Date.from(
                    selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                )
            )
            //Text("Selected Date: $formattedDate")

            DatePicker(
                state = datePickerState,
                dateFormatter = DatePickerDefaults.dateFormatter(
                    yearSelectionSkeleton = "MM YYYY",
                    selectedDateSkeleton = "yyyy MM dd E",
                    selectedDateDescriptionSkeleton = "EEEE, MMMM dd"
                ),
                /*headline = {
                    DatePickerDefaults.DatePickerHeadline(
                        selectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000,
                        displayMode = DisplayMode.Picker,
                        dateFormatter
                    )
                }*/
            )
            //DatePicker(state = datePickerState)
        })
}