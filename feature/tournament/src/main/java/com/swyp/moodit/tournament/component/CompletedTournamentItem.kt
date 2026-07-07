package com.swyp.moodit.tournament.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.tournament.CompletedTournamentDetail

@Composable
fun CompletedTournamentItem(
    modifier: Modifier = Modifier,
    completedTournament: CompletedTournamentDetail,
    onTournamentClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTournamentClick() },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(158f / 182f)
            ) {
                AsyncImage(
                    model = completedTournament.winnerImage,
                    contentDescription = "match_result_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    error = ColorPainter(Color.Gray)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_narrow_right),
                        contentDescription = "상세보기 화살표",
                        tint = MooditTheme.colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = completedTournament.title,
                style = MooditTheme.typography.b2Small,
                color = MooditTheme.colors.tertiary,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun CompletedTournamentDetailPreview() {
    MooditTheme {
        CompletedTournamentItem(
            completedTournament = CompletedTournamentDetail(),
            onTournamentClick = {}
        )
    }
}