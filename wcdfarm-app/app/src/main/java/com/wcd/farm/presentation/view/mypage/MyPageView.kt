package com.wcd.farm.presentation.view.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wcd.farm.R
import com.wcd.farm.presentation.view.home.noRippleClickable
import com.wcd.farm.presentation.viewmodel.MyPageViewModel

@Composable
fun MyPageScreen() {
    val viewModel: MyPageViewModel = hiltViewModel()
    val gardenCropList by viewModel.cropList.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            Text("농장 정보", Modifier.padding(0.dp, 8.dp), fontSize = 16.sp)


            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxSize()
                    .background(Color.White)
                    .noRippleClickable {

                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dog_on_farm),
                    contentDescription = "Farm",
                    modifier = Modifier
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .weight(0.35f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("SSAFY 농장", fontSize = 24.sp)

                    FarmInfo("농장 주소", listOf("광주광역시 광산구 장덕동 982-10"))
                    FarmInfo("시작 날짜", listOf("2024-10-24"))
                    FarmInfo("작물 현황", gardenCropList)
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        InviteFarmButton()
    }

    CropsState()
}

@Composable
fun FarmInfo(field: String, contentList: List<String>) {
    for (i in contentList.indices) {
        val farmInfo = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (i > 0) Color.Transparent else Color.Unspecified
                )
            ) {
                append("$field ")
            }
            withStyle(style = SpanStyle(fontSize = 13.sp)) {
                append(contentList[i])
            }
        }
        Text(farmInfo)
    }
}

@Composable
fun InviteFarmButton() {
    Button(
        onClick = { /* 버튼 클릭 시 실행할 동작 */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A994E)),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(3.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 아이콘 추가
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "crops",
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("내 농장 초대하기", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun CropsState() {
    val viewModel: MyPageViewModel = hiltViewModel()
    val gardenCropList = listOf("1", "2", "3")

    val crtGarden by viewModel.crtGarden.collectAsState()
    var newCropName by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = { viewModel.addCrop(crtGarden, newCropName) }) {
                    Text("작물 추가하기")
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { Text("작물 현황") }
        }, text = {
            Column {
                LazyColumn {
                    items(gardenCropList.size) { crop ->
                        CropState(gardenCropList[crop])
                    }

                }
                AddCropButton()
                InputNewCrop(newCropName) {
                    newCropName = it
                }
            }

        })
}

@Composable
fun CropState(crop: String) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("상추")
        Text("60%")
        Text("성장")
        Icon(imageVector = Icons.Outlined.RestoreFromTrash, contentDescription = "trashCan")
    }
}

@Composable
fun AddCropButton() {
    TextButton(onClick = { /*TODO*/ }) {
        Row {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
            Text("새로운 작물")
        }
    }
}

@Composable
fun InputNewCrop(newCropName: String, onValueChange: (String) -> Unit) {

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(value = newCropName, onValueChange = { onValueChange(it) } )
    }
}