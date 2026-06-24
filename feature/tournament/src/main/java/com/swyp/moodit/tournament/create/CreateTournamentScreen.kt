package com.swyp.moodit.tournament.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.CreateMoodMatchTooltip
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.tournament.component.UploadPhotoItem
import com.swyp.moodit.tournament.component.UploadPhotoStatusCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTournamentScreen(
    onCreateTournamentClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDeletePhotoClick: (String) -> Unit,
    onRetryUploadClick: (SelectedPhoto) -> Unit,
    uiState: CreateTournamentContract.State
) {
    var retryPhoto by remember { mutableStateOf<SelectedPhoto?>(null) }

    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MooditTopBar(
                title = {
                    Text(
                        text = "새 무드매치 만들기",
                        style = MooditTheme.typography.h2
                    )
                }
            )
        },
        bottomBar = {
            MooditFilledButton(
                onClick = { onCreateTournamentClick() },
                enabled = uiState.isTournamentValid,
                text = if (uiState.isTournamentValid) "시작하기" else "사진 준비 중"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "무드매치 제목",
                    color = MooditTheme.colors.onBackground,
                    style = MooditTheme.typography.b2Medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                CreateMoodMatchTooltip()
            }

            OutlinedTextField(
                value = uiState.title,
                onValueChange = {
                    if (it.length <= 20) onTitleChange(it)
                },
                placeholder = {
                    Text(
                        "ex) 봄에 따라 입고 싶은 룩",
                        color = MooditTheme.colors.textSecondary,
                        style = MooditTheme.typography.b3Medium
                    )
                },
                trailingIcon = {
                    if (uiState.title.isNotEmpty()) {
                        IconButton(onClick = { onTitleChange("") }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.Clear,
                                contentDescription = "icon_delete_title",
                                tint = MooditTheme.colors.onSurfaceContainer
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MooditTheme.colors.borderDefault,
                    unfocusedBorderColor = MooditTheme.colors.borderDefault,
                    focusedContainerColor = MooditTheme.colors.onPrimary,
                    unfocusedContainerColor = MooditTheme.colors.onPrimary,
                    focusedTextColor = MooditTheme.colors.onBackground,
                    unfocusedTextColor = MooditTheme.colors.onBackground,
                )
            )

            Text(
                text = "${uiState.title.length}/20",
                color = MooditTheme.colors.borderDefault,
                style = MooditTheme.typography.b3Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            UploadPhotoStatusCard(uiState)

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(MooditTheme.colors.onPrimary, RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                MooditTheme.colors.onSurfaceContainer,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onPhotoPickerClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MooditTheme.colors.surfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "icon_insert_photo",
                                tint = MooditTheme.colors.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                itemsIndexed(uiState.selectedPhotos) { index, photo ->
                    UploadPhotoItem(
                        onDeleteClick = { onDeletePhotoClick(photo.id) },
                        onRetryClick = { retryPhoto = photo },
                        photo = photo
                    )
                }
            }

            retryPhoto?.let { photo ->
                MooditDialog(
                    title = "업로드 재시도",
                    description = "사진 업로드를 다시 시도하시겠습니까?",
                    onClickCancel = { retryPhoto = null }
                ) {
                    MooditFilledButton(
                        onClick = { retryPhoto = null },
                        modifier = Modifier.weight(1f),
                        text = "취소",
                        containerColor = MooditTheme.colors.surfaceContainer,
                        contentColor = MooditTheme.colors.textSecondary
                    )
                    MooditFilledButton(
                        onClick = {
                            onRetryUploadClick(photo)
                            retryPhoto = null
                        },
                        modifier = Modifier.weight(1f),
                        text = "확인"
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CreateTournamentScreenPreview() {
    MaterialTheme {
        CreateTournamentScreen(
            uiState = CreateTournamentContract.State(
                title = "무드매치 제목"
            ),
            onTitleChange = {},
            onPhotoPickerClick = {},
            onDeletePhotoClick = {},
            onRetryUploadClick = {},
            onCreateTournamentClick = {}
        )
    }
}