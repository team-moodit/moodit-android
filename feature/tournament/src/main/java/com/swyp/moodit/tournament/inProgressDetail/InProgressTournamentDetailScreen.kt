package com.swyp.moodit.tournament.inProgressDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.common.util.DateUtil.toDaysAgoMessage
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InProgressTournamentDetailScreen(
    uiState: InProgressTournamentDetailContract.State,
    onResumeClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteCompleteClick: () -> Unit,
    onDeleteDialogShowChange: (Boolean) -> Unit,
    onDeleteCompleteDialogShowChange: (Boolean) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    MooditScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MooditTopBar(
                title = {
                    Text(
                        text = "진행중인 무드매치",
                        style = MooditTheme.typography.h3
                    )
                },
                textAlign = TextAlign.Center,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MooditTheme.colors.background,
                    titleContentColor = MooditTheme.colors.onBackground,
                    scrolledContainerColor = MooditTheme.colors.background
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MooditFilledButton(
                    modifier = Modifier.weight(1f),
                    text = "삭제하기",
                    onClick = { onDeleteDialogShowChange(true) },
                    containerColor = MooditTheme.colors.surfaceContainer,
                    contentColor = MooditTheme.colors.textSecondary
                )
                MooditFilledButton(
                    modifier = Modifier.weight(1f),
                    text = "이어서 진행하기",
                    onClick = { onResumeClick() })
            }
        }
    ) { innerPadding ->
        InProgressTournamentContent(
            innerPadding = innerPadding,
            uiState = uiState,
            onDeleteDialogShowChange = onDeleteDialogShowChange,
            onDeleteCompleteDialogShowChange = onDeleteCompleteDialogShowChange,
            onDeleteClick = onDeleteClick,
            onDeleteCompleteClick = onDeleteCompleteClick
        )
    }
}

@Composable
fun InProgressTournamentContent(
    innerPadding: PaddingValues,
    uiState: InProgressTournamentDetailContract.State,
    onDeleteDialogShowChange: (Boolean) -> Unit,
    onDeleteCompleteDialogShowChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteCompleteClick: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
            end = innerPadding.calculateEndPadding(LayoutDirection.Rtl) + 16.dp,
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding()
        ),
        columns = GridCells.Fixed(3)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 56.dp),
                horizontalAlignment = Alignment.Start
            ) {
                MooditTag(
                    content = "${uiState.tournamentDetail.matchInfo.createdAt.toDaysAgoMessage()} 마지막 진행",
                    modifier = Modifier
                        .wrapContentSize()
                        .border(
                            width = 1.dp,
                            color = MooditTheme.colors.onSurfaceContainer,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    textColor = MooditTheme.colors.tertiary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${uiState.tournamentDetail.totalRound}강 중 ${uiState.tournamentDetail.currentRound}강까지 진행한 무드매치에요\n계속 이어서 진행할까요?",
                    style = MooditTheme.typography.h2,
                    color = MooditTheme.colors.onPrimaryContainer
                )
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "무드매치 정보",
                style = MooditTheme.typography.b1Medium,
                color = MooditTheme.colors.onPrimaryContainer
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MooditTheme.colors.onPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "무드매치 이름",
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.textSecondary
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = uiState.tournamentDetail.title,
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "진행률",
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.textSecondary
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "${uiState.tournamentDetail.currentRound}강 / ${uiState.tournamentDetail.totalRound}강",
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "마지막 진행 날짜",
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.textSecondary
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = uiState.tournamentDetail.matchInfo.createdAt.toFormatDate(),
                            style = MooditTheme.typography.b2ExtraSmall,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                    }
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
                    content = "${uiState.tournamentDetail.matchInfo.totalImageCount}장",
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
            count = uiState.tournamentDetail.images.size,
            key = { index -> uiState.tournamentDetail.images[index].id }
        ) { index ->
            val imageUri = uiState.tournamentDetail.images[index]
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = imageUri.photoUri,
                contentScale = ContentScale.Crop,
                contentDescription = "image",
                error = ColorPainter(Color.Gray)
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (uiState.showDeleteDialog) {
        MooditDialog(
            title = "정말 무드매치를을 삭제하시겠어요?",
            description = "무드매치를 삭제하면 진행중이던 과정이 삭제돼요",
            onClickCancel = { onDeleteDialogShowChange(false) }
        ) {
            MooditFilledButton(
                onClick = { onDeleteDialogShowChange(false) },
                modifier = Modifier.weight(1f),
                text = "취소",
                containerColor = MooditTheme.colors.primary.copy(alpha = 0.1f),
                contentColor = MooditTheme.colors.primary
            )
            MooditFilledButton(
                onClick = {
                    onDeleteDialogShowChange(false)
                    onDeleteClick()
                    onDeleteCompleteDialogShowChange(true)
                },
                modifier = Modifier.weight(1f),
                text = "확인"
            )
        }
    }

    if (uiState.showDeleteCompleteDialog) {
        MooditDialog(
            title = "무드매치를 삭제했어요",
            description = "삭제할 무드매치는 다시 볼 수 없어요",
            onClickCancel = {
                onDeleteCompleteDialogShowChange(false)
                onDeleteCompleteClick()
            },
            icon = {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "icon_delete_account_complete"
                )
            }
        ) {
            MooditFilledButton(
                onClick = {
                    onDeleteCompleteDialogShowChange(false)
                    onDeleteCompleteClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "확인"
            )
        }
    }
}

