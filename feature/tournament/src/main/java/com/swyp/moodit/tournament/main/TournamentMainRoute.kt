package com.swyp.moodit.tournament.main

import androidx.compose.runtime.Composable

@Composable
fun TournamentMainRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    TournamentMainScreen(onShowSnackbar = onShowSnackbar)
}