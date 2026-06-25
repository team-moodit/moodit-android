package com.swyp.moodit.home.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(
    onSettingClick: () -> Unit,
    onCreateTournamentClick: () -> Unit,
    onMissionClick: (Long) -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "새 무드매치 만들기",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        color = MooditTheme.colors.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MooditTheme.colors.background)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "HomeMainScreen", style = MaterialTheme.typography.displayMedium)
            Button(modifier = Modifier.fillMaxWidth(), onClick = onSettingClick) {
                Text(text = "설정 버튼")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = onCreateTournamentClick) {
                Text(text = "새 토너먼트 생성하기")
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMissionClick(1234L) },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "진행 중인 미션 (클릭 시 상세 이동)", fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMissionClick(123L) },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "완료된 미션 (클릭 시 상세 이동)", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}