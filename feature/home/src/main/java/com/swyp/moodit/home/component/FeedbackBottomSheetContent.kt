package com.swyp.moodit.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption

@Composable
fun FeedbackBottomSheetContent(
    selectedDrawback: FeedbackOption? = null,
    onValueChange: (String) -> Unit,
    onConfirmClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "어떤 점이 아쉬웠나요?",
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "더 나은 경험을 제공할 수 있도록 노력할게요",
            style = MooditTheme.typography.b3Medium,
            color = MooditTheme.colors.textSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val feedbackOptions = listOf(
                FeedbackOption(1L, "이 스타일이 저랑 안 맞았어요"),
                FeedbackOption(2L, "경험해보니 생각과 달랐어요"),
                FeedbackOption(3L, "선택 이유와 안 맞는 미션이었어요"),
                FeedbackOption(4L, "수행하기 막막한 미션이었어요")
            )

            feedbackOptions.forEach { feedbackOption ->
                FeedbackOptionItem(
                    feedbackOption = feedbackOption,
                    isSelected = false,
                    onClick = { }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MooditFilledButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirmClick() },
            text = "완료"
        )
    }
}

@Preview
@Composable
fun FeedbackBottomSheetContentPreview() {
    MooditTheme {
        FeedbackBottomSheetContent(
            onValueChange = {},
            onConfirmClick = {}
        )
    }
}