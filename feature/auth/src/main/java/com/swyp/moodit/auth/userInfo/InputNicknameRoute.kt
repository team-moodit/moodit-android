package com.swyp.moodit.auth.userInfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun InputNicknameRoute(
    viewModel: InputNicknameViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMain: () -> Unit,
    navigateToSetting: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is InputNicknameContract.SideEffect.NavigateToSetting -> navigateToSetting()
                is InputNicknameContract.SideEffect.NavigateToMain -> navigateToMain()
                is InputNicknameContract.SideEffect.ShowSnackbar -> onShowSnackbar(
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
                CircularProgressIndicator()
            }
        }

        else -> {
            InputNicknameScreen(
                uiState = uiState,
                onNicknameChange = {
                    viewModel.sendIntent(
                        InputNicknameContract.Intent.OnNicknameChange(
                            it
                        )
                    )
                },
                onConfirmClick = { viewModel.sendIntent(InputNicknameContract.Intent.OnConfirmClick) }
            )
        }
    }
}