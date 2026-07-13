package com.swyp.moodit.tournament.completedDetail

import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionDetailLoadingType
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CompletedTournamentDetailContract {
    data class State(
        val isLoading: MissionDetailLoadingType = MissionDetailLoadingType.NONE,
        val nickname: String = "",
        val tournamentId: Long = 0L,
        val userMissionId: Long = 0L,
        val tournamentDetail: CompletedTournamentDetail = CompletedTournamentDetail(),
        val mission: Mission? = null,
        val selectedTab: CompletedTournamentTab = CompletedTournamentTab.MOOD_MATCH,
        val showSatisfactionBottomSheet: Boolean = false,
        val showFeedbackBottomSheet: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val showDeleteConfirmDialog: Boolean = false,
        val showDeleteCompleteDialog: Boolean = false,
        val slidingRating: Float = 0.0f,
        val selectedFeedback: List<FeedbackOption> = emptyList(),
        val feedbackOptions: List<FeedbackOption> = emptyList()
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToReportReady : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: CompletedTournamentTab) : Intent
        data object LoadMissionInfo : Intent
        data object OnCompleteClick : Intent
        data object OnDeleteClick : Intent
        data object SubmitSatisfaction : Intent
        data object ClearFeedbackOption: Intent
        data object LoadUserInfo: Intent
        data class OnDeleteDialogShowChange(val show: Boolean) : Intent
        data class OnDeleteCompleteDialogShowChange(val show: Boolean) : Intent
        data class OnSatisfactionBottomSheetShowChange(val show: Boolean) : Intent
        data class OnFeedbackBottomSheetShowChange(val show: Boolean) : Intent
        data class OnSliderRatingChange(val rating: Float) : Intent
        data class ToggleFeedbackOption(val option: FeedbackOption): Intent
    }
}

enum class CompletedTournamentTab(val tabName: String) {
    MOOD_MATCH("무드매치"), MISSION("미션")
}