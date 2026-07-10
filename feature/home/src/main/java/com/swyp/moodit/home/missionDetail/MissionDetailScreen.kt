package com.swyp.moodit.home.missionDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.component.mission.MissionDetailContent

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionDetailScreen(
    uiState: MissionDetailContract.State,
    onCompleteClick: () -> Unit,
    onTryButtonClick: () -> Unit,
    onDeleteCompleteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSatisfactionShowChange: (Boolean) -> Unit,
    onFeedbackShowChange: (Boolean) -> Unit,
    onDeleteDialogShowChange: (Boolean) -> Unit,
    onDeleteCompleteDialogShowChange: (Boolean) -> Unit,
    onSliderRatingChange: (Float) -> Unit,
    onToggleFeedbackOption: (FeedbackOption) -> Unit,
    onClearFeedbackOption: () -> Unit,
    submitSatisfaction: () -> Unit
) {
    val navButtonText = when {
        uiState.status == MissionStatus.CREATED -> "해볼래요"
        uiState.missionInfo.missionState == MissionState.COMPLETED -> "만족도 입력하기"
        else -> "미션 완료"
    }

    MooditScaffold(
        bottomBar = {
            val isMissionSettled = uiState.missionInfo.missionState == MissionState.REVIEWED
            if (!isMissionSettled) {
                MooditFilledButton(
                    onClick = {
                        when {
                            uiState.status == MissionStatus.CREATED -> onTryButtonClick()
                            uiState.missionInfo.missionState == MissionState.COMPLETED -> onSatisfactionShowChange(
                                true
                            )

                            else -> onCompleteClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    text = navButtonText
                )
            }
        }
    ) { innerPadding ->
        MissionDetailContent(
            nickname = uiState.nickname,
            missionInfo = uiState.missionInfo,
            missionStatus = uiState.status,
            contentPadding = innerPadding,
            showFeedbackBottomSheet = uiState.showFeedbackBottomSheet,
            showSatisfactionBottomSheet = uiState.showSatisfactionBottomSheet,
            showDeleteDialog = uiState.showDeleteDialog,
            showDeleteCompleteDialog = uiState.showDeleteCompleteDialog,
            slidingRating = uiState.currentSliderRating,
            selectedFeedback = uiState.selectedFeedback,
            feedbackOptions = uiState.feedbackOptions,
            onDeleteClick = onDeleteClick,
            onDeleteCompleteClick = onDeleteCompleteClick,
            onSatisfactionShowChange = onSatisfactionShowChange,
            onFeedbackShowChange = onFeedbackShowChange,
            onDeleteDialogShowChange = onDeleteDialogShowChange,
            onDeleteCompleteDialogShowChange = onDeleteCompleteDialogShowChange,
            onSliderRatingChange = onSliderRatingChange,
            onToggleFeedbackOption = onToggleFeedbackOption,
            onClearFeedbackOption = onClearFeedbackOption,
            submitSatisfaction = submitSatisfaction
        )
    }
}

@Preview
@Composable
fun MissionDetailScreenPreview() {
    MooditTheme {
        MissionDetailScreen(
            uiState = MissionDetailContract.State(
                missionInfo = Mission(missionState = MissionState.COMPLETED)
            ),
            onTryButtonClick = {},
            onCompleteClick = {},
            onDeleteCompleteClick = {},
            onDeleteClick = {},
            onSatisfactionShowChange = {},
            onFeedbackShowChange = {},
            onSliderRatingChange = {},
            onToggleFeedbackOption = {},
            onClearFeedbackOption = {},
            submitSatisfaction = {},
            onDeleteDialogShowChange = {},
            onDeleteCompleteDialogShowChange = {}
        )
    }
}
