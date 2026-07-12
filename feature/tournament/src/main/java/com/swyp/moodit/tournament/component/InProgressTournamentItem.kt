package com.swyp.moodit.tournament.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.tournament.InProgressTournament

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun InProgressTournamentItem(
    modifier: Modifier = Modifier,
    inProgressTournament: InProgressTournament,
    onTournamentClick: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * (0.75f)
    val progressRatio =
        (inProgressTournament.currentMatchProgress / inProgressTournament.finalMatchProgress.toFloat())
            .coerceIn(0f, 1f)

    val currentRoundLabel = when(inProgressTournament.currentRound) {
        2 -> "결승전"
        4 -> "준결승전"
        else -> "${inProgressTournament.currentRound}강"
    }

    Card(
        modifier = modifier
            .width(itemWidth)
            .clickable(onClick = { onTournamentClick() })
            .border(1.dp, MooditTheme.colors.onSurface, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MooditTheme.colors.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    inProgressTournament.title,
                    style = MooditTheme.typography.b2Small,
                    color = MooditTheme.colors.onPrimaryContainer
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    tint = MooditTheme.colors.onTertiary,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "icon_chevron_right"
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentRoundLabel,
                    style = MooditTheme.typography.caption,
                    color = MooditTheme.colors.primary
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(color = MooditTheme.colors.onSurfaceContainer)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressRatio)
                            .fillMaxHeight()
                            .clip(
                                shape = RoundedCornerShape(999.dp)
                            )
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFDCFAA4),
                                        MooditTheme.colors.primary
                                    )
                                )
                            )
                    )
                }
                Text(
                    text = "완료",
                    style = MooditTheme.typography.caption,
                    color = MooditTheme.colors.textSecondary
                )
            }
        }
    }
}

@Preview
@Composable
fun InProgressTournamentDetailPreview() {
    MooditTheme {
        InProgressTournamentItem(
            inProgressTournament = InProgressTournament(),
            onTournamentClick = {}
        )
    }
}