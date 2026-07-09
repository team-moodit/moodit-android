package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun ReportEmptyContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    buttonLabel: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = content, style = MooditTheme.typography.b2Small,
            color = MooditTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.wrapContentWidth(),
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MooditTheme.colors.primary,
                contentColor = MooditTheme.colors.onPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp)
        ) {
            Text(
                text = buttonLabel,
                color = MooditTheme.colors.onPrimary,
                style = MooditTheme.typography.b1Medium
            )
        }
    }
}

@Preview
@Composable
fun ReportEmptyContentPreview() {
    MooditTheme {
        ReportEmptyContent(
            title = "아직 분석할 취향이 없어요",
            content = "무드매치를 시작하고 사진을 선택해보세요\n" +
                    "선택이 쌓일수록 나만의 리포트가 만들어집니다",
            buttonLabel = "새 무드매치 만들기",
            onButtonClick = { }
        )
    }
}