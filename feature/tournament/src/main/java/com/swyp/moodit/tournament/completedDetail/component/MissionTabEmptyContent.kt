package com.swyp.moodit.tournament.completedDetail.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.tournament.completedDetail.CompletedTournamentDetailContract

@Composable
fun MissionTabEmptyContent(
    innerPadding: PaddingValues,
    uiState: CompletedTournamentDetailContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .aspectRatio(0.82f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = uiState.tournamentDetail.winnerImage.photoUri,
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
                        text = "${uiState.nickname}님이 픽한 취향",
                        color = MooditTheme.colors.onPrimaryContainer,
                        style = MooditTheme.typography.caption
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 23.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.empty_mission),
                contentDescription = "icon_mission_satisfaction",
                tint = MooditTheme.colors.onTertiary,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "이번 미션은 패스했어요",
                color = MooditTheme.colors.textSecondary,
                style = MooditTheme.typography.b3Medium
            )
        }
    }
}

@Preview
@Composable
fun MissionTabEmptyContentPreview() {
    MooditTheme {
        MissionTabEmptyContent(
            innerPadding = PaddingValues(),
            uiState = CompletedTournamentDetailContract.State()
        )
    }
}