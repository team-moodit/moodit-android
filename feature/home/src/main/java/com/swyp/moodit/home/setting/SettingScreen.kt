package com.swyp.moodit.home.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onFeedbackClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
) {
    val containerColor = Color(0xFF12141B)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "설정",
                        fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(vertical = 20.dp)
                    .clickable { onDeleteAccountClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "계정 삭제",
                    color = Color.Red,
                    fontSize = 14.sp
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Red
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .clickable { onTermsClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "kakao 로그인", color = Color.White, fontSize = 14.sp)
                Text(text = "example@kakao.com", color = Color(0xFFA4A9BA), fontSize = 14.sp)
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 12.dp,
                color = Color(0xFF1D212A)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .clickable { onPrivacyPolicyClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "이용약관", color = Color.White, fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFBEC3D1)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .clickable { onPrivacyPolicyClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "개인정보 처리방침", color = Color.White, fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFBEC3D1)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "버전 정보", color = Color.White, fontSize = 14.sp)
                Text(text = "v 1.0.0", color = Color(0xFFA4A9BA), fontSize = 14.sp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .clickable { onFeedbackClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "피드백 하기", color = Color.White, fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFBEC3D1)
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 12.dp,
                color = Color(0xFF1D212A)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .clickable { onLogOutClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(text = "로그아웃", color = Color.White, fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "icon_click",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFBEC3D1)
                )
            }
        }
    }
}

@Composable
@Preview
fun SettingScreenPreview() {
    MaterialTheme {
        SettingScreen(
            onTermsClick = {},
            onPrivacyPolicyClick = {},
            onFeedbackClick = {},
            onLogOutClick = {},
            onDeleteAccountClick = {}
        )
    }
}