package com.wcd.farm.presentation.view.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp).weight(0.35f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier.fillMaxWidth().padding(16.dp)) {
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
    }
}

@Preview(showBackground = true, backgroundColor = 0XFFECEDC1, widthDp = 360, heightDp = 720)
@Composable
fun PreviewMyPageScreen() {
    MyPageScreen()
}