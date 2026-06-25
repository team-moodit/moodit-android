package com.swyp.moodit.home.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.MissionTag
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MissionCompletedContent(tagContent: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        MissionTag(content = tagContent)
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
            text = "후보와 비슷한 색감으로\n하루 코디해보기",
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onPrimaryContainer,
            textAlign = TextAlign.Start
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MissionInfoCard(
                modifier = Modifier.weight(1f), title = "진행된 라운드 수", content = "16강"
            )
            MissionInfoCard(
                modifier = Modifier.weight(1f), title = "무드매치 완료 날짜", content = "26.06.10"
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "만족도 평가",
            style = MooditTheme.typography.b1Medium,
            color = MooditTheme.colors.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MooditTheme.colors.surfaceContainer)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.bomb),
                        contentDescription = "icon_mood_result",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "평가 전",
                        style = MooditTheme.typography.b3Medium,
                        color = MooditTheme.colors.tertiary
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "직접 해보니 어땠나요?",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b2Medium
                )

                Text(
                    text = "경험을 남기면 취향의 기준이 뚜렷해져요",
                    color = MooditTheme.colors.textSecondary,
                    style = MooditTheme.typography.b3Medium
                )
            }
        }
    }
}

@Preview
@Composable
fun MissionCompletedContentPreview() {
    MooditTheme {
        MissionCompletedContent(tagContent = "완료")
    }
}