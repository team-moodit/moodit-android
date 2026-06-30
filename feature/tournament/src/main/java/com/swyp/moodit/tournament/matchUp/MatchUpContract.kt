package com.swyp.moodit.tournament.matchUp

import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.Candidate
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.MatchUpReason
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class MatchUpContract {
    data class State(
        val isLoading: Boolean = false,
        val isStarted: Boolean = false,
        val matchUpInfo: MatchUpInfo = MatchUpInfo(),
        val currentStep: TournamentStep = TournamentStep.MATCH_UP,
        val progressFraction: Float = 0f,
        val selectedWinner: Candidate? = null,
        val selectedReason: MatchUpReason? = null,
        val showRetryDialog: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(
            val message: String,
            val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS
        ) : SideEffect

        data class NavigateToResult(val matchResultId: Long) : SideEffect
        object NavigateBack : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnCandidateSelect(val candidate: Candidate) : Intent
        data class OnReasonSelect(val reasonId: Long) : Intent
        object LoadMatchUpInfo : Intent
        object ShowRetrySaveDialog : Intent
        object OnNextButtonClick : Intent
        object OnRetryClick : Intent
        object OnExitClick : Intent
    }
}

enum class TournamentStep {
    MATCH_UP,
    REASON
}