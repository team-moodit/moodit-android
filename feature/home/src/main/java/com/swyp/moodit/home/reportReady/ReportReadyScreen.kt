package com.swyp.moodit.home.reportReady

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun ReportReadyScreen(
    uiState: ReportReadyContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ReportReadyScreen", style = MaterialTheme.typography.displayMedium)
        Button(modifier = Modifier.fillMaxWidth(), onClick = { }) {
            Text(text = "리포트 보러가기")
        }
        Text(
            modifier = Modifier.clickable {}, text = "홈으로 가기", style = TextStyle(
                textDecoration = TextDecoration.Underline
            )
        )
    }
}