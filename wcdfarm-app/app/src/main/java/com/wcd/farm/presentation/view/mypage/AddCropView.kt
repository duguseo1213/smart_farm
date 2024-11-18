// AddCropView.kt

package com.wcd.farm.presentation.view.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcd.farm.presentation.viewmodel.MyPageViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


@Composable
fun AddCropView(onDismiss: () -> Unit) {

    val viewModel: MyPageViewModel = hiltViewModel()
    val gardenCropList by viewModel.cropList.collectAsState()
    val gardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()
    var newCropName by remember {
        mutableStateOf("")
    }
    var showInputField by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0D5D32))
                , horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = {
                    viewModel.addCrop(gardenList[crtGarden!!].gardenId, newCropName)
                    onDismiss() // 닫기
                }) {
                    Text(text = "작물 추가하기", color = Color.White)
                }
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { Text(
                text = "작물 현황",
                color = Color(0xFF0F4327),
                fontWeight = FontWeight.Bold
                ) }
        },
        text = {
            Column {
                LazyColumn {
                    items(gardenCropList.size) { crop ->
                        CropState(gardenCropList[crop])
                    }
                }
                InputNewCrop(newCropName) {
                    newCropName = it
                }
            }
        }
    )
}