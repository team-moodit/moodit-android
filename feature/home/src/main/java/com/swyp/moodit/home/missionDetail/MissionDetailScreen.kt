package com.swyp.moodit.home.missionDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.home.component.FeedbackBottomSheetContent
import com.swyp.moodit.home.component.MissionCompletedContent
import com.swyp.moodit.home.component.MissionCreatedContent
import com.swyp.moodit.home.component.MissionProgressContent
import com.swyp.moodit.home.component.SatisfactionBottomSheetContent
import com.swyp.moodit.navigation.MissionStatus

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionDetailScreen(
    uiState: MissionDetailContract.State,
    onCompleteClick: () -> Unit
) {
    var currentSliderRating by remember { mutableFloatStateOf(1.0f) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSatisfactionBottomSheet by remember { mutableStateOf(false) }
    var showFeedbackBottomSheet by remember { mutableStateOf(false) }
    val navButtonText = when {
        uiState.status == MissionStatus.CREATED -> "해볼래요"
        uiState.isCompleted -> "만족도 입력하기"
        else -> "미션을 완료했어요"
    }
    val tagContent = when {
        uiState.status == MissionStatus.CREATED -> "MISSION"
        uiState.isCompleted -> "완료"
        else -> "진행중"
    }

    MooditScaffold(
        bottomBar = {
            MooditFilledButton(
                onClick = {
                    if (uiState.status == MissionStatus.CREATED)
                        onCompleteClick()
                    else showSatisfactionBottomSheet = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                text = navButtonText
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            AsyncImage(
                model = "https://picsum.photos/200/300",
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
                text = "약속 날 입고 갈 옷",
                style = MooditTheme.typography.h3,
                color = MooditTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(30.dp))

            when {
                uiState.status == MissionStatus.CREATED -> MissionCreatedContent(tagContent)
                uiState.isCompleted -> MissionCompletedContent(tagContent)
                else -> MissionProgressContent(tagContent)
            }

            if (showSatisfactionBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSatisfactionBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = MooditTheme.colors.onPrimary
                ) {
                    SatisfactionBottomSheetContent(
                        currentSliderRating = currentSliderRating,
                        onValueChange = { currentSliderRating = it },
                        onCompleteClick = {
                            showSatisfactionBottomSheet = false
                            if (currentSliderRating <= 2.0) {
                                showFeedbackBottomSheet = true
                            } else {
                                onCompleteClick()
                            }
                        }
                    )
                }
            }

            if (showFeedbackBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showFeedbackBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = MooditTheme.colors.onPrimary
                ) {
                    FeedbackBottomSheetContent(
                        onConfirmClick = {
                            showFeedbackBottomSheet = false
                            onCompleteClick()
                        },
                        onValueChange = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MissionDetailScreenPreview() {
    MooditTheme {
        MissionDetailScreen(
            uiState = MissionDetailContract.State(
                isCompleted = true
            ),
            onCompleteClick = {}
        )
    }
}
