package com.swyp.moodit.report.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.isHeading
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun ReportMainRoute(
    viewModel: ReportMainViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToMissionDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    navigateToCreateMoodMatch: () -> Unit,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val feedbackSubMittedMissions = uiState.feedbackSubMittedMissions.collectAsLazyPagingItems()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        feedbackSubMittedMissions.refresh()
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ReportMainContract.Intent.LoadPreferenceReport)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportMainContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, null)
                }

                is ReportMainContract.SideEffect.NavigateToSetting -> {
                    navigateToSetting()
                }

                is ReportMainContract.SideEffect.NavigateToMissionDetail -> {
                    navigateToMissionDetail(sideEffect.missionId)
                }

                is ReportMainContract.SideEffect.NavigateToHome -> {
                    navigateToHome()
                }

                is ReportMainContract.SideEffect.NavigateToCreateMoodMatch -> {
                    navigateToCreateMoodMatch()
                }
            }
        }
    }

    ReportMainScreen(
        uiState = uiState,
        feedbackSubMittedMissions = feedbackSubMittedMissions,
        onTabClick = { viewModel.sendIntent(ReportMainContract.Intent.SelectTab(it)) },
        onSettingClick = { viewModel.sendIntent(ReportMainContract.Intent.OnSettingClick) },
        onMissionClick = { viewModel.sendIntent(ReportMainContract.Intent.OnMissionClick(it)) },
        onCreateMoodMatchClick = { viewModel.sendIntent(ReportMainContract.Intent.OnCreateMoodMatchClick) },
        onCheckMissionClick = { viewModel.sendIntent(ReportMainContract.Intent.OnCheckMissionClick) }
    )
}