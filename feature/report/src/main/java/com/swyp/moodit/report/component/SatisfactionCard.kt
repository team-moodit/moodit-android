package com.swyp.moodit.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.report.SatisfactionSummary

@Composable
fun SatisfactionCard(
    modifier: Modifier = Modifier,
    satisfactionSummary: SatisfactionSummary
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MooditTheme.colors.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "시도한 취향의 만족도는",
                        style = MooditTheme.typography.b3Medium,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        MooditTag(
                            modifier = Modifier
                                .wrapContentSize()
                                .border(
                                    width = 1.dp,
                                    color = MooditTheme.colors.primary,
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            content = "평균 ${if (satisfactionSummary.rate % 1.0 == 0.0) satisfactionSummary.rate.toInt() else satisfactionSummary.rate}점"
                        )
                        Text(
                            text = "이에요",
                            style = MooditTheme.typography.b3Medium,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (satisfactionSummary.count == 0L) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "${satisfactionSummary.count}개",
                        color = if (satisfactionSummary.count == 0L) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                        style = MooditTheme.typography.caption
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            SatisfactionLinearProgressBar(rate = satisfactionSummary.rate)
        }
    }
}

@Preview
@Composable
fun SatisfactionCardPreview() {
    MooditTheme {
        SatisfactionCard(
            satisfactionSummary = SatisfactionSummary(rate = 4.2)
        )
    }
}