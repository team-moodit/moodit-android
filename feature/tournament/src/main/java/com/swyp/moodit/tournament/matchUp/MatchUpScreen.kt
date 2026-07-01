package com.swyp.moodit.tournament.matchUp

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.component.button.MooditSelectableButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Candidate
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.tournament.component.MoodCandidateItem

@Composable
fun MatchUpScreen(
    uiState: MatchUpContract.State,
    onSelectCandidate: (Candidate) -> Unit,
    onReasonSelect: (Long) -> Unit,
    onNextButtonClick: () -> Unit,
    onExitClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                LinearProgressIndicator(
                    progress = { uiState.progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .height(5.dp),
                    color = MooditTheme.colors.primary,
                    trackColor = MooditTheme.colors.onSurfaceContainer,
                    strokeCap = StrokeCap.Round
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        text = "${uiState.matchUpInfo.curMatchIndex}/${uiState.matchUpInfo.totalRounds}",
                        style = MooditTheme.typography.caption,
                        color = MooditTheme.colors.textSecondary
                    )
                    Text(
                        text = uiState.matchUpInfo.roundTitle,
                        style = MooditTheme.typography.b2ExtraSmall,
                        color = MooditTheme.colors.primary
                    )
                }
            }
        },
        bottomBar = {
            if (uiState.currentStep == TournamentStep.REASON) {
                MooditFilledButton(
                    onClick = { onNextButtonClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = uiState.selectedReason != null,
                    text = if (uiState.matchUpInfo.isCompleted) "결과 보러가기" else "다음"
                )
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
                            onSelectReason = onReasonSelect,
                            onExitClick = onExitClick,
                            onRetryClick = onRetryClick
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
    onSelectPhoto: (Candidate) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = uiState.matchUpInfo.title,
            style = MooditTheme.typography.b2Medium,
            color = MooditTheme.colors.tertiary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "더 마음이 가는 쪽을 골라보세요.",
            style = MooditTheme.typography.b1Large,
            color = MooditTheme.colors.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(24.dp))

        uiState.matchUpInfo.nextMatchUp?.let { nextMatch ->
            val candidateA = nextMatch.candidateA
            val candidateB = nextMatch.candidateB

            MoodCandidateItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .aspectRatio(1f)
                    .clickable { onSelectPhoto(candidateA) },
                isSelected = uiState.selectedWinner == candidateA,
                anyPhotoSelected = uiState.selectedWinner != null,
                moodCandidate = candidateA
            )

            Spacer(modifier = Modifier.height(12.dp))

            MoodCandidateItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .aspectRatio(1f)
                    .clickable { onSelectPhoto(candidateB) },
                isSelected = uiState.selectedWinner == candidateB,
                anyPhotoSelected = uiState.selectedWinner != null,
                moodCandidate = candidateB
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SelectReasonContent(
    uiState: MatchUpContract.State,
    onSelectReason: (Long) -> Unit,
    onExitClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "어떤 점이 더 좋았나요?",
            modifier = Modifier.padding(top = 7.dp, bottom = 20.dp),
            style = MooditTheme.typography.b1Large,
            color = MooditTheme.colors.onBackground
        )

        uiState.matchUpInfo.nextMatchUp?.let { nextMatch ->
            val candidateA = nextMatch.candidateA
            val candidateB = nextMatch.candidateB
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MoodCandidateItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.6f),
                    isSelected = uiState.selectedWinner == candidateA,
                    anyPhotoSelected = uiState.selectedWinner != null,
                    moodCandidate = candidateA
                )

                MoodCandidateItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.6f),
                    isSelected = uiState.selectedWinner == candidateB,
                    anyPhotoSelected = uiState.selectedWinner != null,
                    moodCandidate = candidateB
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            uiState.matchUpInfo.reasons.forEach { reason ->
                MooditSelectableButton(
                    content = reason.content,
                    isSelected = reason.id == uiState.selectedReason?.id,
                    onItemClick = { onSelectReason(reason.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.showRetryDialog) {
            MooditDialog(
                title = "저장하지 못했어요",
                description = "다시 시도하거나 그냥 나갈 수 있어요."
            ) {
                MooditFilledButton(
                    onClick = { onExitClick() },
                    modifier = Modifier.weight(1f),
                    text = "그냥 나가기",
                    containerColor = MooditTheme.colors.surfaceContainer,
                    contentColor = MooditTheme.colors.textSecondary
                )
                MooditFilledButton(
                    onClick = { onRetryClick() },
                    modifier = Modifier.weight(1f),
                    text = "다시 시도"
                )
            }
        }
    }
}

@Preview
@Composable
fun MatchUpScreenPreview() {
    MooditTheme {
        MatchUpScreen(
            uiState = MatchUpContract.State(

            ),
            onSelectCandidate = {},
            onReasonSelect = {},
            onNextButtonClick = {},
            onExitClick = {},
            onRetryClick = {}
        )
    }
}

@Composable
@Preview
fun SelectPhotoContentPreview() {
    MaterialTheme {
        SelectPhotoContent(
            uiState = MatchUpContract.State(),
            onSelectPhoto = {}
        )
    }
}

@Composable
@Preview
fun SelectReasonContentPreview() {
    MaterialTheme {
        SelectReasonContent(
            uiState = MatchUpContract.State(matchUpInfo = MatchUpInfo(roundTitle = "예선전")),
            onSelectReason = {},
            onExitClick = {},
            onRetryClick = {}
        )
    }
}
