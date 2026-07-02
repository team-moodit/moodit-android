package com.swyp.moodit.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult

@Composable
fun MissionReviewedItemCard(
    modifier: Modifier = Modifier,
    mission: Mission,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(158f / 254f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = mission.matchResult.imageUrl,
                contentDescription = "image_mission",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .weight(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = mission.missionTitle,
                    style = MooditTheme.typography.b2Small,
                    color = MooditTheme.colors.onPrimaryContainer,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.star_filled),
                            contentDescription = "icon_star",
                            modifier = Modifier.size(10.dp),
                            tint = MooditTheme.colors.primary
                        )
                        Text(
                            text = "${mission.satisfactionScore}",
                            style = MooditTheme.typography.caption,
                            color = MooditTheme.colors.primary
                        )
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        text = mission.missionCompletedAt.toFormatDate(),
                        style = MooditTheme.typography.caption,
                        color = MooditTheme.colors.borderDefault
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MissionReviewItemCardPreview() {
    MooditTheme {
        MissionReviewedItemCard(
            mission = Mission(
                matchResult = MissionMatchResult(
                    imageUrl = "https://example.com/image.jpg",
                ),
                missionTitle = "이 색감과 비슷하게 코디해보기",
                satisfactionScore = 2.5f,
                missionCompletedAt = "26.06.12"
            ),
            onClick = {}
        )
    }
}