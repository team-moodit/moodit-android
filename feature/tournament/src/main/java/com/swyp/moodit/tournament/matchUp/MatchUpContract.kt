package com.swyp.moodit.tournament.matchUp

import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class MatchUpContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentTitle: String = "토너먼트 제목",
        val currentRoundTitle: String = "",
        val currentStep: TournamentStep = TournamentStep.MATCH_UP,
        val currentMatchUp: MatchUp? = null,
        val selectedWinner: MoodCandidate? = null,
        val currentMatchIndex: Int = 0,
        val totalMatchUpInCurrentRound: Int = 0,
        val isMatchCompleted: Boolean = false,
        val selectedReason: MoodReason? = null,
        val reasons: List<MoodReason> = emptyList(),
        val showRetryDialog: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String, val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS) : SideEffect
        data class NavigateToResult(val winnerPhotoId: Long) : SideEffect
        object NavigateBack : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnCandidateSelect(val candidate: MoodCandidate) : Intent
        data class OnReasonSelect(val reasonId: Long) : Intent
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

data class MatchUp(
    val candidateA: MoodCandidate,
    val candidateB: MoodCandidate
)

data class MoodCandidate(val id: Long, val photoUri: String, val name: String)

data class MoodReason(val id: Long = 0L, val content: String = "", val isSelected: Boolean = false)