package com.swyp.moodit.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.home.component.MissionItemCard
import com.swyp.moodit.model.Mission
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(
    onSettingClick: () -> Unit,
    onCreateTournamentClick: () -> Unit,
    onMissionClick: (Long) -> Unit,
    inProgressMissions: LazyPagingItems<Mission>,
    completedMissions: LazyPagingItems<Mission>,
    feedbackSubMittedMissions: LazyPagingItems<Mission>
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MooditTopBar(
                title = {
                    Text(
                        text = "비비님,\n오늘의 취향을 찾아볼까요?",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        color = MooditTheme.colors.onBackground
                    )
                },
                actionIcon = {
                    Icon(
                        painter = painterResource(R.drawable.setting),
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onSettingClick() },
                        contentDescription = "icon_setting"
                    )
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Button(
                    onClick = onCreateTournamentClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MooditTheme.colors.primary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(start = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "무드매치 만들기",
                                    style = MooditTheme.typography.b1Large,
                                    color = MooditTheme.colors.onPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = "icon_chevron_right",
                                    modifier = Modifier.size(20.dp),
                                    tint = MooditTheme.colors.onSurfaceContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "이미지를 고르며 내 취향을 찾아봐요",
                                style = MooditTheme.typography.b3Small,
                                color = MooditTheme.colors.onPrimary,
                                textAlign = TextAlign.Start
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.subtract),
                            contentDescription = "icon_subtract",
                            modifier = Modifier.size(80.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "선택한 취향을 시도해봐요",
                        style = MooditTheme.typography.b2Medium,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = MooditTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${inProgressMissions.itemCount}개",
                            color = MooditTheme.colors.primary,
                            style = MooditTheme.typography.caption
                        )
                    }
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = inProgressMissions.itemCount,
                        key = { index ->
                            inProgressMissions[index]?.userMissionId ?: index
                        }) { index ->
                        val mission = inProgressMissions[index]
                        if (mission != null)
                            MissionItemCard(
                                mission = mission,
                                onClick = { onMissionClick(mission.userMissionId) })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "경험한 취향을 기록해보세요",
                        style = MooditTheme.typography.b2Medium,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = MooditTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${completedMissions.itemCount}개",
                            color = MooditTheme.colors.primary,
                            style = MooditTheme.typography.caption
                        )
                    }
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = completedMissions.itemCount,
                        key = { index ->
                            completedMissions[index]?.userMissionId ?: index
                        }) { index ->
                        val mission = completedMissions[index]
                        if (mission != null)
                            MissionItemCard(
                                mission = mission,
                                onClick = { onMissionClick(mission.userMissionId) })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "완료한 취향 기록을 모아봤어요",
                        style = MooditTheme.typography.b2Medium,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = MooditTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "${feedbackSubMittedMissions.itemCount}개",
                            color = MooditTheme.colors.primary,
                            style = MooditTheme.typography.caption
                        )
                    }
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = feedbackSubMittedMissions.itemCount,
                        key = { index ->
                            feedbackSubMittedMissions[index]?.userMissionId ?: index
                        }) { index ->
                        val mission = feedbackSubMittedMissions[index]
                        if (mission != null)
                            MissionItemCard(
                                mission = mission,
                                onClick = { onMissionClick(mission.userMissionId) })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeMainScreenPreview() {
    val emptyMissionsFlow = remember {
        flowOf(PagingData.from(emptyList<Mission>()))
    }
    MooditTheme {
        HomeMainScreen(
            onMissionClick = {},
            onSettingClick = {},
            onCreateTournamentClick = {},
            completedMissions = emptyMissionsFlow.collectAsLazyPagingItems(),
            inProgressMissions = emptyMissionsFlow.collectAsLazyPagingItems(),
            feedbackSubMittedMissions = emptyMissionsFlow.collectAsLazyPagingItems()
        )
    }
}