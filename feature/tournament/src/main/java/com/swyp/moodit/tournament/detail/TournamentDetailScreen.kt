package com.swyp.moodit.tournament.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun TournamentDetailScreen(
    uiState: TournamentDetailContract.State,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isCompleted) {
            CompletedTournamentContent()
        } else {
            OnGoingTournamentContent(onDeleteClick = onDeleteClick)
        }
    }
}

@Composable
fun OnGoingTournamentContent(
    onDeleteClick: () -> Unit
) {
    Text(text = "OnGoingTournamentScreen", style = MaterialTheme.typography.displayMedium)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { onDeleteClick() }) {
            Text(text = "삭제하기")
        }
        Button(onClick = {}) {
            Text(text = "이어서 진행하기")
        }
    }
}

@Composable
fun CompletedTournamentContent() {
    Text(text = "CompletedTournamentScreen", style = MaterialTheme.typography.displayMedium)
}