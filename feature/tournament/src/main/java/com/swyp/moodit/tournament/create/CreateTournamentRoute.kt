package com.swyp.moodit.tournament.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CreateTournamentRoute(
    viewModel: CreateTournamentViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToMatchUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(32)
    ) { uris ->
        viewModel.sendIntent(CreateTournamentContract.Intent.OnPhotoChange(photoUris = uris.map { it.toString() }))
    }

    LaunchedEffect(uiState.showPhotoPicker) {
        if (uiState.showPhotoPicker) {
            pickMultipleMedia.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
            viewModel.sendIntent(CreateTournamentContract.Intent.OnPhotoPickerStateChange(false))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CreateTournamentContract.SideEffect.NavigateToMatchUp -> navigateToMatchUp()
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
                    Text(text = "내 취향을 찾기 위한\n무드매치를 준비하고 있어요", textAlign = TextAlign.Center)
                    CircularProgressIndicator()
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