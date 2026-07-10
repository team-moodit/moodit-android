package com.swyp.moodit.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.report.Distribution
import com.swyp.moodit.model.report.PreferenceDetail
import com.swyp.moodit.model.report.PreferenceReport
import com.swyp.moodit.model.report.ResultType

@Composable
fun ReportPreferenceCard(
    modifier: Modifier = Modifier,
    totalMatchCount: Long,
    preferenceReport: PreferenceReport,
) {
    val top3Distributions: List<Distribution> = preferenceReport.distributions
        .sortedBy {
            if (preferenceReport.resultType == ResultType.PREFERENCE_DETAIL) {
                it.percentage
            } else {
                it.percentage
            }
        }
        .takeLast(3)

    val isTieResult = preferenceReport.resultType == ResultType.PREFERENCE_TIE

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MooditTheme.colors.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                    when (preferenceReport.resultType) {
                        ResultType.PREFERENCE_DETAIL -> {
                            Text(
                                text = "그 중에서도",
                                style = MooditTheme.typography.b3Large,
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
                                            color = Color(0xFF536F21),
                                            shape = RoundedCornerShape(100.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp),
                                    content = preferenceReport.topPreferenceDetail.insightTitle,
                                    textStyle = MooditTheme.typography.b1Small
                                )
                                Text(
                                    text = "${TextUtil.attachSecondParticle(preferenceReport.topPreferenceDetail.insightTitle)} 가장 중요한 척도에요",
                                    style = MooditTheme.typography.b3Large,
                                    color = MooditTheme.colors.onPrimaryContainer
                                )
                            }
                        }

                        ResultType.PREFERENCE_ONLY -> {
                            val percentageText =
                                if (preferenceReport.topPreference.percentage % 1.0 == 0.0) {
                                    preferenceReport.topPreference.percentage.toInt().toString()
                                } else {
                                    preferenceReport.topPreference.percentage.toString()
                                }

                            Text(
                                text = "전체 선택 중 ${percentageText}%가",
                                style = MooditTheme.typography.b3Large,
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
                                            color = Color(0xFF536F21),
                                            shape = RoundedCornerShape(100.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 4.dp),
                                    content = preferenceReport.topPreference.title,
                                    textStyle = MooditTheme.typography.b1Small
                                )
                                Text(
                                    text = "관련 기준이었어요",
                                    style = MooditTheme.typography.b3Large,
                                    color = MooditTheme.colors.onPrimaryContainer
                                )
                            }
                        }

                        ResultType.PREFERENCE_TIE -> {
                            Text(
                                text = "여러가지 기준을 참고해서\n신중히 판단하고 있어요",
                                style = MooditTheme.typography.b1Small,
                                color = MooditTheme.colors.onPrimaryContainer
                            )
                        }

                        ResultType.NONE -> null
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val countColor =
                        if (totalMatchCount == 0L) MooditTheme.colors.onSurface else MooditTheme.colors.primary

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(color = countColor, shape = CircleShape)
                    )
                    Text(
                        text = "${totalMatchCount}개",
                        color = countColor,
                        style = MooditTheme.typography.caption
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                top3Distributions.forEachIndexed { index, detail ->
                    val labelText = when (preferenceReport.resultType) {
                        ResultType.PREFERENCE_DETAIL -> detail.detailType
                        ResultType.PREFERENCE_ONLY -> detail.title
                        else -> detail.title
                    }
                    val rank = 3 - index
                    PreferenceDistributionItem(
                        modifier = Modifier.weight(1f),
                        preference = detail,
                        isTie = isTieResult,
                        rank = rank
                    )
                }
            }
        }
    }
}

@Composable
fun PreferenceDistributionItem(
    modifier: Modifier = Modifier,
    preference: Distribution,
    isTie: Boolean,
    rank: Int
) {
    val weightValue = (preference.percentage / 100.0).toFloat().coerceIn(0.01f, 1f)
    val spacerWeight = (1f - weightValue).coerceAtLeast(0.01f)
    val barColor = when {
        isTie -> MooditTheme.colors.textSecondary
        rank == 1 -> MooditTheme.colors.primary
        rank == 2 -> MooditTheme.colors.textSecondary.copy(alpha = 0.6f)
        else -> MooditTheme.colors.textSecondary
    }

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.weight(spacerWeight)
        )

        Text(
            text = "${if (preference.percentage % 1.0 == 0.0) preference.percentage.toInt() else preference.percentage}%",
            style = MooditTheme.typography.caption,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .padding(horizontal = 8.dp)
                .weight(weightValue, fill = true)
                .heightIn(min = 24.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )
                )
                .background(color = barColor)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = preference.title,
            style = MooditTheme.typography.caption,
            color = MooditTheme.colors.onPrimaryContainer
        )
    }
}

@Preview
@Composable
fun ReportPreferenceCardPreview() {
    MooditTheme {
        ReportPreferenceCard(
            totalMatchCount = 10,
            preferenceReport = PreferenceReport(
                topPreference = PreferenceDetail(
                    type = "123",
                    title = "나와의 적합도",
                    selectedCount = 10L,
                    percentage = 50.0
                ),
                distributions = listOf(
                    Distribution(
                        type = "123",
                        title = "지속성",
                        selectedCount = 10,
                        percentage = 20.0
                    ), Distribution(
                        type = "123",
                        title = "심미성",
                        selectedCount = 10,
                        percentage = 24.0
                    ), Distribution(
                        type = "123",
                        title = "나와의 적합도",
                        selectedCount = 10,
                        percentage = 50.0
                    )
                )
            ),
        )
    }
}