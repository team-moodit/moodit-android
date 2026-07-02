package com.swyp.moodit.report.main

import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class ReportMainContract {
    data class State(
        val isLoading: Boolean = false,
        val selectedTab: ReportTab = ReportTab.REPORT,
        val reportSummary: ReportSummary = ReportSummary()
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(
            val message: String,
            val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS
        ) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: ReportTab) : Intent
    }
}

enum class ReportTab(val tabName: String) { REPORT("종합 리포트"), SATISFACTION("완료한 미션") }