package com.swyp.moodit.ui.component.mission

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.MissionStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionDetailContent(
    modifier: Modifier = Modifier,
    missionInfo: Mission,
    missionStatus: MissionStatus,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    showSatisfactionBottomSheet: Boolean,
    showFeedbackBottomSheet: Boolean,
    slidingRating: Float,
    selectedFeedback: List<FeedbackOption>,
    feedbackOptions: List<FeedbackOption>,
    onCompleteClick: () -> Unit,
    onSatisfactionShowChange: (Boolean) -> Unit,
    onFeedbackShowChange: (Boolean) -> Unit,
    onSliderRatingChange: (Float) -> Unit,
    onToggleFeedbackOption: (FeedbackOption) -> Unit,
    submitSatisfaction: () -> Unit
) {
    val tagContent = when {
        missionStatus == MissionStatus.CREATED -> "MISSION"
        missionInfo.missionState == MissionState.COMPLETED -> "완료"
        else -> "진행중"
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(contentPadding)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        AsyncImage(
            model = missionInfo.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .aspectRatio(0.82f)
                .clip(RoundedCornerShape(16.dp)),
            contentDescription = "img_result",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = missionInfo.matchTitle,
            style = MooditTheme.typography.h3,
            color = MooditTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(30.dp))

        when {
            missionStatus == MissionStatus.CREATED -> MissionCreatedContent(
                tagContent,
                missionInfo
            )

            missionInfo.missionState == MissionState.COMPLETED -> MissionCompletedContent(
                tagContent, missionInfo
            )

            missionInfo.missionState == MissionState.IN_PROGRESS -> MissionProgressContent(
                tagContent, missionInfo
            )
        }
    }

    if (showSatisfactionBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onSatisfactionShowChange(false) },
            sheetState = sheetState,
            containerColor = MooditTheme.colors.onPrimary
        ) {
            val window = (LocalView.current.parent as DialogWindowProvider).window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            SatisfactionBottomSheetContent(
                currentSliderRating = slidingRating,
                onValueChange = { onSliderRatingChange(it) },
                onCompleteClick = {
                    onSatisfactionShowChange(false)
                    if (slidingRating < 3.0) {
                        onFeedbackShowChange(true)
                    } else {
                        onCompleteClick()
                    }
                }
            )
        }
    }

    if (showFeedbackBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onFeedbackShowChange(false) },
            sheetState = sheetState,
            containerColor = MooditTheme.colors.onPrimary
        ) {
            val window = (LocalView.current.parent as DialogWindowProvider).window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            FeedbackBottomSheetContent(
                selectedFeedback = selectedFeedback,
                feedbackOptions = feedbackOptions,
                onConfirmClick = {
                    onFeedbackShowChange(false)
                    submitSatisfaction()
                },
                onOptionClick = { feedbackOption -> onToggleFeedbackOption(feedbackOption) },
            )
        }
    }
}