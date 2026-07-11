package com.swyp.moodit.home.component

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult

@Composable
fun MissionItemCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mission: Mission
) {
    Card(
        modifier = modifier
            .width(188.dp)
            .aspectRatio(188f / 312f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = mission.matchResult.imageUrl,
                contentDescription = "Style Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            ) {
                AsyncImage(
                    model = mission.matchResult.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .matchParentSize()
                        .blur(
                            radiusX = 20.dp,
                            radiusY = 20.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.45f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = mission.missionTitle,
                        style = MooditTheme.typography.b2Small,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = mission.matchResult.matchTitle,
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.tertiary
                        )

                        Icon(
                            painter = painterResource(R.drawable.arrow_narrow_right),
                            contentDescription = "icon_arrow_narrow_right",
                            tint = MooditTheme.colors.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MissionItemCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF11161B)), // 원본 스크린샷 배경색 반영
        contentAlignment = Alignment.Center
    ) {
        MissionItemCard(
            onClick = {},
            mission = Mission(
                missionTitle = "나의 체형에 잘 어울리는지\n직접 시도해보기", matchResult = MissionMatchResult(
                    matchTitle = "봄에 따라입고 싶은 룩"
                )
            )
        )
    }
}