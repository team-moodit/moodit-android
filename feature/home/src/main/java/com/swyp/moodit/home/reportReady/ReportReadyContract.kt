package com.swyp.moodit.home.reportReady

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class ReportReadyContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToReport : SideEffect
        data object NavigateToHome: SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnNavigateReportClick : Intent
        data object OnNavigateHomeClick : Intent
    }
}