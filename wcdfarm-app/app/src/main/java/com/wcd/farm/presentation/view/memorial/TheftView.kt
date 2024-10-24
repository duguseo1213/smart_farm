package com.wcd.farm.presentation.view.memorial

import androidx.collection.emptyIntList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TheftScreen() {
    val theftList = emptyIntList()
    val listState = rememberLazyListState()

    if (theftList.isEmpty()) {
        EmptyListView(notify = "도난 이력이 없습니다.")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(theftList.size) { animal ->
            InvasionView()
        }
    }
}