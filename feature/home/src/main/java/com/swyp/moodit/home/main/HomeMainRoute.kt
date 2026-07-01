package com.swyp.moodit.home.main

import android.util.Log
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.MissionStatus

@Composable
fun HomeMainRoute(
    viewModel: HomeMainViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToSetting: () -> Unit,
    navigateToCreateTournament: () -> Unit,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inProgressMissions = uiState.inProgressMissions.collectAsLazyPagingItems()
    val completedMissions = uiState.completedMissions.collectAsLazyPagingItems()
    val feedbackSubMittedMissions = uiState.feedbackSubMittedMissions.collectAsLazyPagingItems()

    LaunchedEffect(inProgressMissions.loadState) {
        inProgressMissions.refresh()
    }

    LaunchedEffect(completedMissions.loadState) {
        Log.d("PagingStatus", "completedMissionsLoadState: ${completedMissions.loadState.refresh}")
    }

    LaunchedEffect(feedbackSubMittedMissions.loadState) {
        Log.d("PagingStatus", "feedbackSubMittedMissionsLoadState: ${feedbackSubMittedMissions.loadState.refresh}")
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeMainContract.SideEffect.NavigateToSetting -> navigateToSetting()
                is HomeMainContract.SideEffect.NavigateToCreateTournament -> navigateToCreateTournament()
                is HomeMainContract.SideEffect.NavigateToMissionDetail -> navigateToMissionDetail(
                    sideEffect.missionId,
                    sideEffect.status
                )

                is HomeMainContract.SideEffect.ShowSnackbar -> onShowSnackbar(
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
            HomeMainScreen(
                inProgressMissions = inProgressMissions,
                completedMissions = completedMissions,
                feedbackSubMittedMissions = feedbackSubMittedMissions,
                onSettingClick = { viewModel.sendIntent(HomeMainContract.Intent.OnSettingClick) },
                onCreateTournamentClick = { viewModel.sendIntent(HomeMainContract.Intent.OnCreateTournamentClick) },
                onMissionClick = { missionId ->
                    viewModel.sendIntent(
                        HomeMainContract.Intent.OnMissionClick(
                            missionId
                        )
                    )
                }
            )
        }
    }
}