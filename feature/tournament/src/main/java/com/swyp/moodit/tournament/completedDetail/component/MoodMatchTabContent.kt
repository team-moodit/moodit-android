package com.swyp.moodit.tournament.completedDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.PreferenceResultType
import com.swyp.moodit.tournament.completedDetail.CompletedTournamentDetailContract

@Composable
fun MoodMatchTabContent(
    innerPadding: PaddingValues,
    uiState: CompletedTournamentDetailContract.State
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
            end = innerPadding.calculateEndPadding(LayoutDirection.Rtl) + 16.dp,
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding()
        ),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 16.dp)
                    .aspectRatio(0.82f)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = uiState.tournamentDetail.winnerImage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = ColorPainter(Color.Gray)
                )
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 9.dp)
                            .clip(RoundedCornerShape(16.dp)),
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
                            text = "김비비님이 픽한 취향",
                            color = MooditTheme.colors.onPrimaryContainer,
                            style = MooditTheme.typography.caption
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.tournamentDetail.title,
                    style = MooditTheme.typography.h2,
                    color = MooditTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                /*
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val iconId =
                        if (uiState.tournamentDetail.missionInfo.matchResult.preferenceResultType == PreferenceResultType.TIE) {
                            R.drawable.face_sad_tear
                        } else {
                            R.drawable.reward_stars
                        }

                    val findTasteMessage =
                        if (uiState.tournamentDetail.missionInfo.matchResult.preferenceResultType == PreferenceResultType.TIE)
                            "이번 무드매치는\n뚜렷한 취향의 기준이 없었어요"
                        else {
                            "이번 무드매치는\n${uiState.tournamentDetail.missionInfo.matchResult.matchPreferenceTypeTitle}${
                                TextUtil.attachParticle(
                                    uiState.tournamentDetail.missionInfo.matchResult.matchPreferenceTypeTitle
                                )
                            } 가장 중요하게 생각했어요"
                        }
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = if (uiState.tournamentDetail.missionInfo.matchResult.preferenceResultType == PreferenceResultType.TIE)
                                    MooditTheme.colors.surfaceContainer
                                else
                                    MooditTheme.colors.primary.copy(
                                        0.1f
                                    )
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(iconId),
                            contentDescription = "icon_mood_result",
                            tint = if (uiState.tournamentDetail.missionInfo.matchResult.preferenceResultType == PreferenceResultType.TIE)
                                MooditTheme.colors.onTertiary
                            else
                                MooditTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = findTasteMessage,
                        color = MooditTheme.colors.tertiary,
                        style = MooditTheme.typography.b3Medium
                    )
                } */

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = MooditTheme.colors.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "무드매치 완료 날짜",
                        style = MooditTheme.typography.b3Medium,
                        color = MooditTheme.colors.textSecondary
                    )
                    Text(
                        text = uiState.tournamentDetail.completedAt.toFormatDate(),
                        style = MooditTheme.typography.b3Medium,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "선택한 사진",
                    style = MooditTheme.typography.b1Medium,
                    color = MooditTheme.colors.onPrimaryContainer
                )
                MooditTag(
                    content = "${uiState.tournamentDetail.imageUris.size}장",
                    modifier = Modifier
                        .wrapContentSize()
                        .border(
                            width = 1.dp,
                            color = MooditTheme.colors.onSurfaceContainer,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    textColor = MooditTheme.colors.tertiary
                )
            }
        }

        items(
            count = uiState.tournamentDetail.imageUris.size,
            key = { index -> uiState.tournamentDetail.imageUris[index] }
        ) { index ->
            val imageUri = uiState.tournamentDetail.imageUris[index]
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = "image",
                error = ColorPainter(Color.Gray)
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun MoodMatchTabContentPreview() {
    MooditTheme {
        MoodMatchTabContent(
            innerPadding = PaddingValues(),
            uiState = CompletedTournamentDetailContract.State()
        )
    }
}