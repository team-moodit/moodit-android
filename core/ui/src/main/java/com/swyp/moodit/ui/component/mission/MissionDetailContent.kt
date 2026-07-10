package com.swyp.moodit.ui.component.mission

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.MissionStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionDetailContent(
    modifier: Modifier = Modifier,
    nickname: String,
    missionInfo: Mission,
    missionStatus: MissionStatus,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    showSatisfactionBottomSheet: Boolean,
    showFeedbackBottomSheet: Boolean,
    showDeleteDialog: Boolean,
    showDeleteCompleteDialog: Boolean,
    slidingRating: Float,
    selectedFeedback: List<FeedbackOption>,
    feedbackOptions: List<FeedbackOption>,
    onDeleteClick: () -> Unit,
    onDeleteCompleteClick: () -> Unit,
    onSatisfactionShowChange: (Boolean) -> Unit,
    onFeedbackShowChange: (Boolean) -> Unit,
    onDeleteDialogShowChange: (Boolean) -> Unit,
    onDeleteCompleteDialogShowChange: (Boolean) -> Unit,
    onSliderRatingChange: (Float) -> Unit,
    onToggleFeedbackOption: (FeedbackOption) -> Unit,
    onClearFeedbackOption: () -> Unit,
    submitSatisfaction: () -> Unit
) {
    val tagContent = when {
        missionStatus == MissionStatus.CREATED -> "MISSION"
        missionInfo.missionState == MissionState.COMPLETED || missionInfo.missionState == MissionState.REVIEWED -> "완료"
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .aspectRatio(232f / 309f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = missionInfo.matchResult.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = ColorPainter(Color.Gray)
            )
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(100.dp),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(R.drawable.star_filled),
                        contentDescription = "icon_tag",
                        tint = Color.White
                    )

                    Text(
                        text = "${nickname}님이 픽한 취향",
                        color = MooditTheme.colors.onPrimaryContainer,
                        style = MooditTheme.typography.caption
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = missionInfo.matchResult.matchTitle,
            style = MooditTheme.typography.h3,
            color = MooditTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(30.dp))

        when {
            missionStatus == MissionStatus.CREATED -> MissionCreatedContent(
                tagContent,
                missionInfo
            )

            missionInfo.missionState == MissionState.COMPLETED || missionInfo.missionState == MissionState.REVIEWED -> MissionCompletedContent(
                tagContent, missionInfo
            )

            missionInfo.missionState == MissionState.IN_PROGRESS -> MissionProgressContent(
                tagContent, missionInfo, onMissionDeleteClick = { onDeleteDialogShowChange(true) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    if (showSatisfactionBottomSheet) {
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
                currentSliderRating = slidingRating,
                onValueChange = { onSliderRatingChange(it) },
                onCompleteClick = {
                    onSatisfactionShowChange(false)
                    if (slidingRating < 3.0) {
                        onFeedbackShowChange(true)
                    } else {
                        onSatisfactionShowChange(false)
                        submitSatisfaction()
                    }
                }
            )
        }
    }

    if (showFeedbackBottomSheet) {
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

    if (showDeleteDialog) {
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

    if (showDeleteCompleteDialog) {
        MooditDialog(
            title = "미션을 삭제했어요",
            description = "삭제할 미션은 다시 볼 수 없어요",
            onClickCancel = {
                onDeleteCompleteDialogShowChange(false)
                onDeleteCompleteClick()
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
                    onDeleteCompleteClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "확인"
            )
        }
    }
}