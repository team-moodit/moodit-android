package com.swyp.moodit.tournament.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.StarPurple500
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val (statusText, statusColor, statusIcon) = when {
        hasError -> Triple(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("불러오지 못한 사진이 있어요.")
                }
            },
            Color.Red,
            Icons.Default.Info
        )

        isLoading -> Triple(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("사진을 불러오고 있어요.")
                }
            },
            Color(0xFF4CAF50),
            Icons.Default.AddPhotoAlternate
        )

        else -> Triple(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${totalCount}장")
                }
                withStyle(style = SpanStyle(color = Color.White)) {
                    append(" 선택됨 · ")
                }
                withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                    append("${totalCount}강")
                }
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("으로 시작해요!")
                }
            },
            Color(0xFF4CAF50),
            Icons.Default.StarPurple500
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = statusIcon,
            contentDescription = "icon_state",
            tint = statusColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = statusText,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        val countText = if (isLoading) {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$successCount")
                }
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("/${totalCount}장")
                }
            }
        } else if (hasError) {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$successCount")
                }
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("/${totalCount}장")
                }
            }

        } else {
            null
        }

        // 상태 표시 점
        if (countText != null) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(statusColor, CircleShape)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = countText,
                fontSize = 14.sp,
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