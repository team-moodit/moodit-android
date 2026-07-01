package com.swyp.moodit.tournament.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus
import com.swyp.moodit.tournament.create.CreateTournamentContract

@Composable
fun UploadPhotoStatusCard(
    uiState: CreateTournamentContract.State,
    modifier: Modifier = Modifier
) {
    val totalCount = uiState.selectedPhotos.size
    val successCount = remember(uiState.selectedPhotos) {
        uiState.selectedPhotos.count { it.status is UploadStatus.Success }
    }
    val hasError = remember(uiState.selectedPhotos) {
        uiState.selectedPhotos.any { it.status is UploadStatus.Error }
    }
    val isLoading = remember(uiState.selectedPhotos) {
        !hasError && uiState.selectedPhotos.any { it.status == UploadStatus.Loading }
    }
    val isEmpty = remember(uiState.selectedPhotos) {
        uiState.selectedPhotos.isEmpty()
    }

    val (statusText, statusColor, statusIcon) = when {
        hasError -> Triple(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MooditTheme.colors.onPrimaryContainer)) {
                    append("불러오지 못한 사진이 있어요.")
                }
            },
            MooditTheme.colors.error,
            Icons.Default.Info
        )

        isLoading -> Triple(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MooditTheme.colors.onPrimaryContainer)) {
                    append("사진을 불러오고 있어요.")
                }
            },
            MooditTheme.colors.primary,
            Unit
        )

        isEmpty -> Triple(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MooditTheme.colors.onPrimaryContainer)) {
                    append("8장부터 32장까지 선택할 수 있어요")
                }
            },
            MooditTheme.colors.primary,
            R.drawable.star_filled
        )

        else -> Triple(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = MooditTheme.colors.primary)
                ) {
                    append("${totalCount}장")
                }
                withStyle(style = SpanStyle(color = MooditTheme.colors.borderDefault)) {
                    append(" 선택됨 · ")
                }
                withStyle(style = SpanStyle(color = MooditTheme.colors.primary)) {
                    append("${totalCount}강")
                }
                withStyle(style = SpanStyle(color = MooditTheme.colors.borderDefault)) {
                    append("으로 시작해요!")
                }
            },
            MooditTheme.colors.primary,
            R.drawable.star_filled
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MooditTheme.colors.surfaceContainer,
                RoundedCornerShape(16.dp)
            )
            .background(MooditTheme.colors.primaryContainer, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = statusColor,
                    strokeWidth = 2.5.dp
                )
            }

            statusIcon is Int -> {
                Icon(
                    painter = painterResource(statusIcon),
                    contentDescription = "icon_state",
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            statusIcon is ImageVector -> {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = "icon_state",
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = statusText,
            style = MooditTheme.typography.b3Medium,
            modifier = Modifier.weight(1f)
        )

        val countText = if (isLoading) {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MooditTheme.colors.primary
                    )
                ) {
                    append("$successCount")
                }
                withStyle(style = SpanStyle(color = MooditTheme.colors.borderDefault)) {
                    append("/${totalCount}장")
                }
            }
        } else if (hasError) {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MooditTheme.colors.error
                    )
                ) {
                    append("$successCount")
                }
                withStyle(style = SpanStyle(color = MooditTheme.colors.borderDefault)) {
                    append("/${totalCount}장")
                }
            }

        } else {
            null
        }

        if (countText != null) {
            Text(
                text = countText,
                style = MooditTheme.typography.caption
            )
        }
    }
}

@Composable
@Preview
fun UploadPhotoStatusCardPreview() {
    MaterialTheme {
        UploadPhotoStatusCard(
            uiState = CreateTournamentContract.State(
                selectedPhotos = listOf(SelectedPhoto(uri = ""))
            )
        )
    }
}