package com.swyp.moodit.onboard.report

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
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun ReportScreen(
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ReportScreen", style = MaterialTheme.typography.displayMedium)
        Button(modifier = Modifier.fillMaxWidth(), onClick = onStartClick) {
            Text(text = "시작하기")
        }
    }
}