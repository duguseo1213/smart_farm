package com.wcd.farm.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.viewmodel.HomeViewModel

@Composable
fun NewPictureView() {
    val viewModel: HomeViewModel = mavericksViewModel()
    val gardenList by viewModel.gardenList.collectAsState()
    val newPicture by viewModel.newPicture.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()

    var description by remember {
        mutableStateOf("")
    }

    AlertDialog(onDismissRequest = { }, dismissButton = {
        TextButton(onClick = { viewModel.showNewPicture(null) }) {
            Text("취소")
        }
    }, confirmButton = {
        TextButton(
            onClick = {
                viewModel.addNewPicture(
                    newPicture!!,
                    description,
                    gardenList[crtGarden!!].gardenId
                )
            }) {
            Text("저장")
        }
    }, text = {
        Column {
            AsyncImage(model = newPicture, contentDescription = "")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = description, onValueChange = { description = it })
        }
    })
}