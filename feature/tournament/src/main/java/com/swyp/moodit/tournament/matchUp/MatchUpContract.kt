package com.swyp.moodit.tournament.matchUp

import android.view.WindowInsets
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
        val selectedWinner: TasteCandidate? = null
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToResult(val winnerPhotoId: Long): SideEffect
        object NavigateBack: SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnCandidateSelect(val candidate: TasteCandidate) : Intent
        data class OnReasonSelect(val reasonId: Long) : Intent
        object OnBackStepClick : Intent
    }
}

enum class TournamentStep {
    MATCH_UP,
    REASON
}

data class MatchUp(
    val candidateA: TasteCandidate,
    val candidateB: TasteCandidate? = null
)

data class TasteCandidate(val id: Long, val photoUri: String, val name: String)