package com.swyp.moodit.tournament.matchUp

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swyp.moodit.tournament.component.MoodCandidateItem
import com.swyp.moodit.tournament.component.MoodReasonItem

@Composable
fun MatchUpScreen(
    uiState: MatchUpContract.State,
    onSelectCandidate: (MoodCandidate) -> Unit,
    onReasonSelect: (Long) -> Unit,
    onNextButtonClick: () -> Unit
) {
    val progressFraction = if (uiState.totalMatchUpInCurrentRound > 0) {
        uiState.currentMatchIndex.toFloat() / uiState.totalMatchUpInCurrentRound
    } else {
        0f
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp),
                    color = Color(0xFFC4F768),
                    trackColor = Color(0xFF3F4454)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "${uiState.currentMatchIndex} / ${uiState.totalMatchUpInCurrentRound}")
                    Text(text = uiState.currentRoundTitle)
                }
            }
        },
        bottomBar = {
            if (uiState.currentStep == TournamentStep.REASON) {
                Button(
                    onClick = { onNextButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = uiState.selectedReason != null
                ) { Text(text = if (uiState.isMatchCompleted) "결과 보러가기" else "다음") }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Crossfade(targetState = uiState.currentStep, label = "StepTransition") { step ->
                when (step) {
                    TournamentStep.MATCH_UP -> {
                        SelectPhotoContent(
                            uiState = uiState,
                            onSelectPhoto = onSelectCandidate
                        )
                    }

                    TournamentStep.REASON -> {
                        SelectReasonContent(
                            uiState = uiState,
                            onSelectReason = onReasonSelect
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectPhotoContent(
    uiState: MatchUpContract.State,
    onSelectPhoto: (MoodCandidate) -> Unit
) {
    val isWalkOver = uiState.currentMatchUp?.candidateB == null
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = uiState.tournamentTitle, fontSize = 14.sp)
        Text(
            text = if (uiState.currentMatchUp?.candidateB == null) "부전승으로 이 사진이 올라가요." else "더 마음이 가는 쪽을 골라보세요.",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.currentMatchUp != null) {
            MoodCandidateItem(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(horizontal = 16.dp)
                    .clickable { onSelectPhoto(uiState.currentMatchUp.candidateA) },
                isSelected = uiState.selectedWinner == uiState.currentMatchUp.candidateA,
                anyPhotoSelected = uiState.selectedWinner != null,
                moodCandidate = uiState.currentMatchUp.candidateA
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (!isWalkOver) {
                MoodCandidateItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(horizontal = 16.dp)
                        .clickable { onSelectPhoto(uiState.currentMatchUp.candidateB) },
                    isSelected = uiState.selectedWinner == uiState.currentMatchUp.candidateB,
                    anyPhotoSelected = uiState.selectedWinner != null,
                    moodCandidate = uiState.currentMatchUp.candidateB
                )
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Yellow)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SelectReasonContent(
    uiState: MatchUpContract.State,
    onSelectReason: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "어떤 점이 더 좋았나요?", modifier = Modifier.padding(vertical = 24.dp))

        if (uiState.currentMatchUp?.candidateB != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MoodCandidateItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.72f),
                    isSelected = uiState.selectedWinner == uiState.currentMatchUp.candidateA,
                    anyPhotoSelected = uiState.selectedWinner != null,
                    moodCandidate = uiState.currentMatchUp.candidateA
                )

                MoodCandidateItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.72f),
                    isSelected = uiState.selectedWinner == uiState.currentMatchUp.candidateB,
                    anyPhotoSelected = uiState.selectedWinner != null,
                    moodCandidate = uiState.currentMatchUp.candidateB
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            uiState.reasons.forEach { reason ->
                MoodReasonItem(
                    reason = reason,
                    onReasonClick = { onSelectReason(reason.id) }
                )
            }
        }
    }
}

@Composable
@Preview
fun SelectPhotoContentPreview() {
    MaterialTheme {
        SelectPhotoContent(uiState = MatchUpContract.State(), onSelectPhoto = {})
    }
}

@Composable
@Preview
fun SelectReasonContentPreview() {
    MaterialTheme {
        SelectReasonContent(uiState = MatchUpContract.State(), onSelectReason = {})
    }
}
