package com.swyp.moodit.tournament.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TournamentMainScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Text(text = "TournamentMainScreen")
}