package com.wcd.farm.presentation.view.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.airbnb.mvrx.compose.mavericksViewModel
import com.wcd.farm.R
import com.wcd.farm.data.model.CropDTO
import com.wcd.farm.presentation.view.home.noRippleClickable
import com.wcd.farm.presentation.viewmodel.HomeViewModel
import com.wcd.farm.presentation.viewmodel.InfoViewModel
import com.wcd.farm.presentation.viewmodel.MyPageViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
fun MyPageScreen() {
    val viewModel: MyPageViewModel = hiltViewModel()
    val gardenCropList by viewModel.cropList.collectAsState()
    val infoViewModel: InfoViewModel = hiltViewModel()
    val gardenState by infoViewModel.gardenState.collectAsState()
    val homeViewModel: HomeViewModel = mavericksViewModel()
    val crtGarden by homeViewModel.crtGarden.collectAsState()
    val gardenList by homeViewModel.gardenList.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getCrops(gardenList[crtGarden!!].gardenId)
    }

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
                AsyncImage(
                    model = gardenList[crtGarden!!].gardenImage,
                    //painter = painterResource(id = R.drawable.dog_on_farm),
                    contentDescription = "",
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .weight(0.5f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = gardenList[crtGarden!!].gardenName,
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.bookend_semibold)),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    val cropStateList =
                        gardenCropList.map { crop -> "${crop.cropName}   ${crop.growthPercentage}%" }
                    FarmInfo("농장 주소", listOf(gardenList[crtGarden!!].gardenAddress))
                    FarmInfo(
                        "시작 날짜", listOf(
                            ZonedDateTime.parse(gardenList[crtGarden!!].gardenCreated).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            )
                        )
                    )
                    FarmInfo("작물 현황", cropStateList)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawBehind {
                                drawLine(
                                    Color.Black,
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, 0f),
                                    strokeWidth = 1f
                                )
                            }
                            .padding(top = 15.dp)
                            .noRippleClickable { showDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Add Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("농작물 관리", color = Color.Black, fontSize = 14.sp)
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        InviteFarmButton()
    }

    if (showDialog) {
        AddCropView(onDismiss = { showDialog = false }) // Dialog 연결
    }
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
fun CropState(crop: CropDTO) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val gardenList by viewModel.gardenList.collectAsState()
    val crtGarden by viewModel.crtGarden.collectAsState()
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp), // 내부 패딩
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = crop.cropName, // 전달된 crop 값 출력
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)) {
                    append("${crop.growthPercentage}% ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) { // 성장 글씨 크기 줄임
                    append("성장")
                }
            },
            color = Color(0xFF0F4327)
        )

        Icon(
            imageVector = Icons.Outlined.DeleteOutline,
            contentDescription = "Trash Icon",
            tint = Color.Black,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable {
                    viewModel.deleteCrop(
                        gardenList[crtGarden!!].gardenId,
                        crop.cropName
                    )
                }
        )
    }
}


@Composable
fun InputNewCrop(newCropName: String, onValueChange: (String) -> Unit) {

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(value = newCropName, onValueChange = { onValueChange(it) })
    }
}