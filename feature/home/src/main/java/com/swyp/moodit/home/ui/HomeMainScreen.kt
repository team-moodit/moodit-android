package com.swyp.moodit.home.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeMainScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onSettingClick: () -> Unit
) {
    Text(text = "HomeMainScreen")
    Button(onClick = onSettingClick) {
        Text(text = "설정 버튼")
    }
}