package com.swyp.moodit.report.main

import androidx.paging.PagingData
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReportMainContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedTab: ReportTab = ReportTab.REPORT,
        val reportSummary: ReportSummary = ReportSummary(),
        val feedbackSubMittedMissions: Flow<PagingData<Mission>> = flowOf(PagingData.empty())
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(
            val message: String,
            val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS
        ) : SideEffect

        data class NavigateToMissionDetail(val missionId: Long) : SideEffect
        data object NavigateToSetting : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: ReportTab) : Intent
        data class OnMissionClick(val missionId: Long) : Intent
        data object OnSettingClick : Intent
        data object LoadPreferenceReport : Intent
    }
}

enum class ReportTab(val tabName: String) { REPORT("종합 리포트"), SATISFACTION("완료한 미션") }