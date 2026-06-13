package com.swyp.moodit.home.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeMainScreen(
    onSettingClick: () -> Unit,
    onCreateRoundClick: () -> Unit,
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

        Button(modifier = Modifier.fillMaxWidth(), onClick = onCreateRoundClick) {
            Text(text = "새 라운드 생성하기")
        }
    }
}