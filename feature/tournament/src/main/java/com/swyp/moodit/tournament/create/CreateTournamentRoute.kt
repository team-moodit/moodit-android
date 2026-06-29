package com.swyp.moodit.tournament.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.component.MooditLottie
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun CreateTournamentRoute(
    viewModel: CreateTournamentViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMatchUp: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val maxTotalPhotos = 32
    val remainingPhotos = (maxTotalPhotos - uiState.selectedPhotos.size)
    val pickerMaxItems = remainingPhotos.coerceAtLeast(2)

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(pickerMaxItems)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.sendIntent(CreateTournamentContract.Intent.OnPhotoChange(photoUris = uris.map { it.toString() }))
        }
    }

    val pickSingleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.sendIntent(
                CreateTournamentContract.Intent.OnPhotoChange(
                    photoUris = listOf(
                        uri.toString()
                    )
                )
            )
        }
    }

    LaunchedEffect(uiState.showPhotoPicker) {
        if (uiState.showPhotoPicker) {
            val request =
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            if (remainingPhotos == 1) {
                pickSingleMedia.launch(request)
            } else if (remainingPhotos >= 2) {
                pickMultipleMedia.launch(request)
            }
            viewModel.sendIntent(CreateTournamentContract.Intent.OnPhotoPickerStateChange(false))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CreateTournamentContract.SideEffect.NavigateToMatchUp -> navigateToMatchUp(
                    sideEffect.tournamentId
                )

                is CreateTournamentContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )
            }
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "내 취향을 찾기 위한\n무드매치를 준비하고 있어요",
                        textAlign = TextAlign.Center,
                        style = MooditTheme.typography.h2,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(150.dp))
                    MooditLottie()
                }
            }
        }

        else -> {
            CreateTournamentScreen(
                uiState = uiState,
                onCreateTournamentClick = { viewModel.sendIntent(CreateTournamentContract.Intent.OnCreateTournamentClick) },
                onPhotoPickerClick = {
                    viewModel.sendIntent(
                        CreateTournamentContract.Intent.OnPhotoPickerStateChange(
                            true
                        )
                    )
                },
                onTitleChange = {
                    viewModel.sendIntent(
                        CreateTournamentContract.Intent.OnTitleChange(
                            it
                        )
                    )
                },
                onDeletePhotoClick = {
                    viewModel.sendIntent(
                        CreateTournamentContract.Intent.OnDeletePhotoClick(
                            it
                        )
                    )
                },
                onRetryUploadClick = {
                    viewModel.sendIntent(
                        CreateTournamentContract.Intent.OnRetryPhotoUpload(
                            it
                        )
                    )
                }
            )
        }
    }
}