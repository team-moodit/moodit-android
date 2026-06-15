package com.swyp.moodit.tournament.matchUp

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MatchUpScreen(
    uiState: MatchUpContract.State,
    onCandidateSelect: (TasteCandidate) -> Unit,
    onReasonSelect: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = uiState.currentStep, label = "StepTransition") { step ->
            when (step) {
                TournamentStep.MATCH_UP -> {
                    val matchUpInfo = uiState.currentMatchUp
                    if (matchUpInfo != null) {
                        Column {
                            Text(
                                text = uiState.tournamentTitle,
                                style = MaterialTheme.typography.displayMedium
                            )
                            Text(
                                text = uiState.currentRoundTitle,
                                style = MaterialTheme.typography.displaySmall
                            )
                            SelectCandidateContent(
                                matchUp = matchUpInfo,
                                onSelectCandidate = onCandidateSelect
                            )
                        }
                    }
                }

                TournamentStep.REASON -> {
                    val matchUp = uiState.currentMatchUp
                    if (matchUp != null) {
                        Column {
                            Text(
                                text = uiState.tournamentTitle,
                                style = MaterialTheme.typography.displayMedium
                            )
                            Text(
                                text = uiState.currentRoundTitle,
                                style = MaterialTheme.typography.displaySmall
                            )
                            SelectReasonContent(
                                winnerTaste = uiState.selectedWinner,
                                matchUp = matchUp,
                                onSelectReason = onReasonSelect
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectCandidateContent(
    matchUp: MatchUp,
    onSelectCandidate: (TasteCandidate) -> Unit
) {
    if (matchUp.candidateB == null) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "이번 라운드 대결 상대가 없어 부전승으로 진출합니다!",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clickable { onSelectCandidate(matchUp.candidateA) },
                contentAlignment = Alignment.Center
            ) {
                TasteCandidateItem(matchUp.candidateA)
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelectCandidate(matchUp.candidateA) },
                contentAlignment = Alignment.Center
            ) {
                TasteCandidateItem(matchUp.candidateA)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelectCandidate(matchUp.candidateB) },
                contentAlignment = Alignment.Center
            ) {
                TasteCandidateItem(matchUp.candidateB)
            }
        }
    }
}

@Composable
fun SelectReasonContent(
    winnerTaste: TasteCandidate?,
    matchUp: MatchUp,
    onSelectReason: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (matchUp.candidateA == winnerTaste) {
                        Text(text = "선택된 취향")
                    }
                    TasteCandidateItem(matchUp.candidateA)
                }
            }

            if (matchUp.candidateB != null) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (matchUp.candidateB == winnerTaste) {
                            Text(text = "선택된 취향")
                        }
                        TasteCandidateItem(matchUp.candidateB)
                    }
                }
            }
        }

        Text(text = "이유 선택하기")

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "이유 1", modifier = Modifier.clickable { onSelectReason(1L) })
            Text(text = "이유 2", modifier = Modifier.clickable { onSelectReason(2L) })
            Text(text = "이유 3", modifier = Modifier.clickable { onSelectReason(3L) })
            Text(text = "이유 4", modifier = Modifier.clickable { onSelectReason(4L) })
        }
    }
}

@Composable
fun TasteCandidateItem(
    tasteCandidate: TasteCandidate
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = tasteCandidate.photoUri,
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, color = MaterialTheme.colorScheme.onSecondaryContainer),
            contentDescription = ""
        )
        Text(text = tasteCandidate.name, style = MaterialTheme.typography.titleMedium)
    }
}