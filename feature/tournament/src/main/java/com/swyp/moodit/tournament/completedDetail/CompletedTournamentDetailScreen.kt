package com.swyp.moodit.tournament.completedDetail

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.tournament.completedDetail.component.CompletedTournamentTabRow
import com.swyp.moodit.tournament.completedDetail.component.MissionTabContent
import com.swyp.moodit.tournament.completedDetail.component.MissionTabEmptyContent
import com.swyp.moodit.tournament.completedDetail.component.MoodMatchTabContent
import com.swyp.moodit.ui.component.mission.FeedbackBottomSheetContent
import com.swyp.moodit.ui.component.mission.SatisfactionBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTournamentDetailScreen(
    uiState: CompletedTournamentDetailContract.State,
    onTabClick: (CompletedTournamentTab) -> Unit,
    onSatisfactionShowChange: (Boolean) -> Unit,
    onFeedbackShowChange: (Boolean) -> Unit,
    onSliderRatingChange: (Float) -> Unit,
    onToggleFeedbackOption: (FeedbackOption) -> Unit,
    onClearFeedbackOption: () -> Unit,
    submitSatisfaction: () -> Unit,
    onDeleteDialogShowChange: (Boolean) -> Unit,
    onDeleteCompleteDialogShowChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        bottomBar = {
            if (uiState.selectedTab == CompletedTournamentTab.MISSION) {
                when (uiState.mission?.missionState) {
                    MissionState.IN_PROGRESS -> MooditFilledButton(
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        ), onClick = { onCompleteClick() }, text = "미션을 완료했어요"
                    )

                    MissionState.COMPLETED -> MooditFilledButton(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { onSatisfactionShowChange(true) },
                        text = "만족도 입력하기"
                    )

                    else -> null
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            CompletedTournamentTabRow(
                selectedTab = uiState.selectedTab, onTabClick = onTabClick
            )
            when (uiState.selectedTab) {
                CompletedTournamentTab.MOOD_MATCH -> MoodMatchTabContent(innerPadding, uiState)
                CompletedTournamentTab.MISSION ->
                    if (uiState.mission == null) {
                        MissionTabEmptyContent(innerPadding, uiState)
                    } else {
                        MissionTabContent(
                            onMissionDeleteClick = { onDeleteDialogShowChange(true) },
                            innerPadding,
                            uiState
                        )
                    }
            }
        }

        if (uiState.showSatisfactionBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    onSatisfactionShowChange(false)
                    onSliderRatingChange(0.0f)
                },
                sheetState = sheetState,
                containerColor = MooditTheme.colors.onPrimary
            ) {
                val window = (LocalView.current.parent as DialogWindowProvider).window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }
                SatisfactionBottomSheetContent(
                    currentSliderRating = uiState.slidingRating,
                    onValueChange = { onSliderRatingChange(it) },
                    onCompleteClick = {
                        onSatisfactionShowChange(false)
                        if (uiState.slidingRating < 3.0) {
                            onFeedbackShowChange(true)
                        } else {
                            onSatisfactionShowChange(false)
                            submitSatisfaction()
                        }
                    }
                )
            }
        }

        if (uiState.showFeedbackBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    onFeedbackShowChange(false)
                    onSliderRatingChange(0.0f)
                    onClearFeedbackOption()
                },
                sheetState = sheetState,
                containerColor = MooditTheme.colors.onPrimary
            ) {
                val window = (LocalView.current.parent as DialogWindowProvider).window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }
                FeedbackBottomSheetContent(
                    selectedFeedback = uiState.selectedFeedback,
                    feedbackOptions = uiState.feedbackOptions,
                    onConfirmClick = {
                        onFeedbackShowChange(false)
                        submitSatisfaction()
                    },
                    onOptionClick = { feedbackOption -> onToggleFeedbackOption(feedbackOption) },
                )
            }
        }

        if (uiState.showDeleteDialog) {
            MooditDialog(
                title = "정말 미션을 삭제하시겠어요?",
                description = "삭제한 미션은 다시 진행할 수 없어요",
                onClickCancel = { onDeleteDialogShowChange(false) }
            ) {
                MooditFilledButton(
                    onClick = { onDeleteDialogShowChange(false) },
                    modifier = Modifier.weight(1f),
                    text = "취소",
                    containerColor = MooditTheme.colors.primary.copy(alpha = 0.1f),
                    contentColor = MooditTheme.colors.primary
                )
                MooditFilledButton(
                    onClick = {
                        onDeleteDialogShowChange(false)
                        onDeleteClick()
                        onDeleteCompleteDialogShowChange(true)
                    },
                    modifier = Modifier.weight(1f),
                    text = "삭제할래요"
                )
            }
        }

        if (uiState.showDeleteCompleteDialog) {
            MooditDialog(
                title = "미션을 삭제했어요",
                description = "삭제할 미션은 다시 볼 수 없어요",
                onClickCancel = {
                    onDeleteCompleteDialogShowChange(false)
                },
                icon = {
                    Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "icon_delete_account_complete"
                    )
                }
            ) {
                MooditFilledButton(
                    onClick = {
                        onDeleteCompleteDialogShowChange(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "확인"
                )
            }
        }
    }
}