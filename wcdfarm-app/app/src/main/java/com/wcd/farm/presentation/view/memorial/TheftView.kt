package com.wcd.farm.presentation.view.memorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.presentation.viewmodel.MemorialViewModel

@Composable
fun TheftScreen() {
    val viewModel: MemorialViewModel = mavericksViewModel()
    val harmList by viewModel.harmList.collectAsState()
    val listState = rememberLazyListState()

    if (harmList.isEmpty()) {
        EmptyListView(notify = "도난 이력이 없습니다.")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(harmList) { harm ->
            if(harm.harmTarget == "사람") {
                InvasionView(harm, "침입")
            }
        }
    }
}