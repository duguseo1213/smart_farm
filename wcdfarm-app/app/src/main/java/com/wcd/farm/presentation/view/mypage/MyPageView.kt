package com.wcd.farm.presentation.view.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gigamole.composeshadowsplus.rsblur.rsBlurShadow
import com.wcd.farm.R

@Composable
fun MyPageScreen() {
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
                    .background(Color.White),
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
                    val farmAddress = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("농장 주소 ")
                        }
                        withStyle(style = SpanStyle(fontSize = 13.sp)) {
                            append("광주광역시 광산구 장덕동 982-10")
                        }
                    }
                    Text(farmAddress)

                    val farmStartDate = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("시작 날짜 ")
                        }
                        withStyle(style = SpanStyle(fontSize = 13.sp)) {
                            append("2024-10-24")
                        }
                    }
                    Text(farmStartDate)
                }
            }
        }
        // 버튼에 텍스트 추가
        Spacer(modifier = Modifier.height(50.dp))
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
                Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 사이의 간격
                Text("내 농장 초대하기", color = Color.White, fontSize = 16.sp)
            }
        }
}
0}

