package com.swyp.moodit.tournament.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus

@Composable
fun UploadPhotoItem(
    photo: SelectedPhoto,
    onRetryClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(MooditTheme.colors.onPrimary)
    ) {
        SubcomposeAsyncImage(
            model = photo.uri,
            contentDescription = "img_upload",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        ) {
            val isLoading = photo.status is UploadStatus.Loading
            val isError = photo.status is UploadStatus.Error
            val isSuccess = photo.status is UploadStatus.Success

            when {
                isError -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MooditTheme.colors.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.info_triangle),
                            tint = MooditTheme.colors.onSurface,
                            contentDescription = "icon_image_error"
                        )
                    }
                }

                isLoading -> {
                    SubcomposeAsyncImageContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.5f)
                    )

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 3.dp,
                            color = MooditTheme.colors.primary
                        )
                    }
                }

                isSuccess -> {
                    SubcomposeAsyncImageContent(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // DeleteButton
        if (photo.status is UploadStatus.Success) {
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(22.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    contentColor = MooditTheme.colors.tertiary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "icon_delete",
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // RetryButton
        if (photo.status is UploadStatus.Error) {
            IconButton(
                onClick = onRetryClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(22.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.55f),
                    contentColor = MooditTheme.colors.tertiary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "icon_retry",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun UploadPhotoItemSuccessCasePreview() {
    val successCase = SelectedPhoto(
        id = "exampleId",
        uri = "https://placehold.co/600x400",
        status = UploadStatus.Success("")
    )
    MaterialTheme {
        UploadPhotoItem(
            successCase,
            onRetryClick = {},
            onDeleteClick = {}
        )
    }
}

@Composable
@Preview
fun UploadPhotoItemLoadingCasePreview() {
    val loadingCase = SelectedPhoto(
        id = "exampleId",
        uri = "https://placehold.co/600x400",
        status = UploadStatus.Loading
    )
    MaterialTheme {
        UploadPhotoItem(
            loadingCase,
            onRetryClick = {},
            onDeleteClick = {}
        )
    }
}

@Composable
@Preview
fun UploadPhotoItemErrorCasePreview() {
    val errorCase = SelectedPhoto(
        id = "exampleId",
        uri = "https://placehold.co/600x400",
        status = UploadStatus.Error("")
    )
    MaterialTheme {
        UploadPhotoItem(
            errorCase,
            onRetryClick = {},
            onDeleteClick = {}
        )
    }
}