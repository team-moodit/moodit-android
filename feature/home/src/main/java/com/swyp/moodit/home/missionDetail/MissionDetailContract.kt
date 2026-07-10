package com.swyp.moodit.home.missionDetail

import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class MissionDetailContract {
    data class State(
        val isLoading: MissionDetailLoadingType = MissionDetailLoadingType.NONE,
        val nickname: String = "",
        val status: MissionStatus = MissionStatus.DEFAULT,
        val missionInfo: Mission = Mission(),
        val selectedFeedback: List<FeedbackOption> = emptyList(),
        val feedbackOptions: List<FeedbackOption> = emptyList(),
        val showSatisfactionBottomSheet: Boolean = false,
        val showFeedbackBottomSheet: Boolean = false,
        val showDeleteDialog: Boolean = false,
        val showDeleteCompleteDialog: Boolean = false,
        val currentSliderRating: Float = 0.0f,
        val successId: Long = 0L
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String, val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS) : SideEffect
        data object NavigateToReportReady : SideEffect
        data object NavigateToHome : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnTryButtonClick : Intent
        data object OnCompleteClick : Intent
        data object OnDeleteClick : Intent
        data object OnDeleteCompleteClick: Intent
        data object LoadMissionDetail : Intent
        data object SubmitSatisfaction : Intent
        data object ClearFeedbackOption : Intent
        data class OnDeleteCompleteDialogShowChange(val show: Boolean) : Intent
        data class OnDeleteDialogShowChange(val show: Boolean) : Intent
        data class OnSatisfactionShowChange(val show: Boolean) : Intent
        data class OnFeedbackShowChange(val show: Boolean) : Intent
        data class OnSliderRatingChange(val rating: Float) : Intent
        data class ToggleFeedbackOption(val option: FeedbackOption) : Intent
    }
}

enum class MissionDetailLoadingType {
    NONE,
    DEFAULT,
    REPORT
}