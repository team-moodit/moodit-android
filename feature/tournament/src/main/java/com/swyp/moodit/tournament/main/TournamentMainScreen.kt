package com.swyp.moodit.tournament.main

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
fun TournamentMainScreen(
    onTournamentClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "TournamentMainScreen", style = MaterialTheme.typography.displayMedium)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onTournamentClick("OnGoingTournamentId") }) {
            Text(text = "진행 중인 토너먼트")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onTournamentClick("CompletedTournamentId") }) {
            Text(text = "완료한 토너먼트")
        }
    }
}