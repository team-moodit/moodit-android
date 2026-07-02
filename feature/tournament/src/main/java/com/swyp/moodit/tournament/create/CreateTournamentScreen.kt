package com.swyp.moodit.tournament.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
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
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onCreateTournamentClick() },
                enabled = uiState.isTournamentValid,
                text = "시작하기"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
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
                    onTitleChange(it)
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
                        IconButton(
                            onClick = { onTitleChange("") },
                            modifier = Modifier
                                .padding(4.dp)
                                .size(24.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MooditTheme.colors.onSurfaceContainer,
                                contentColor = MooditTheme.colors.onTertiary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "icon_delete",
                                modifier = Modifier.size(16.dp)
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
                    cursorColor = MooditTheme.colors.onBackground,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Text(
                text = "${uiState.title.length}/15",
                color = MooditTheme.colors.borderDefault,
                style = MooditTheme.typography.b3Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 4.dp),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(28.dp))

            UploadPhotoStatusCard(uiState)

            Spacer(modifier = Modifier.height(40.dp))

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
                            Image(
                                painter = painterResource(R.drawable.gallery_button),
                                contentDescription = "icon_insert_photo",
                                modifier = Modifier.size(40.dp)
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
                    title = "이 사진을 다시 불러올까요?",
                    description = "불러오지 못한 사진을 다시 불러올게요",
                    onClickCancel = { retryPhoto = null }
                ) {
                    MooditFilledButton(
                        onClick = {
                            onDeletePhotoClick(photo.id)
                            retryPhoto = null
                        },
                        modifier = Modifier.weight(1f),
                        text = "삭제",
                        containerColor = MooditTheme.colors.surfaceContainer,
                        contentColor = MooditTheme.colors.textSecondary
                    )
                    MooditFilledButton(
                        onClick = {
                            onRetryUploadClick(photo)
                            retryPhoto = null
                        },
                        modifier = Modifier.weight(1f),
                        text = "다시 시도"
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