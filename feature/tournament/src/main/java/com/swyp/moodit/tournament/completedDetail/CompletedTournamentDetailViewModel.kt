package com.swyp.moodit.tournament.completedDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.MissionDetailLoadingType
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository,
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository
) :
    BaseViewModel<CompletedTournamentDetailContract.State, CompletedTournamentDetailContract.Intent, CompletedTournamentDetailContract.SideEffect>(
        initialState = CompletedTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.CompletedDetail>().tournamentId,
            userMissionId = savedStateHandle.toRoute<TournamentRoute.CompletedDetail>().userMissionId,
            feedbackOptions = loadFeedbackOptions()
        )
    ) {

    init {
        loadTournamentInfo()
    }

    override fun handleIntents(intent: CompletedTournamentDetailContract.Intent) {
        when (intent) {
            is CompletedTournamentDetailContract.Intent.SelectTab -> updateTab(intent.tab)
            is CompletedTournamentDetailContract.Intent.LoadMissionInfo -> loadMissionInfo()
            is CompletedTournamentDetailContract.Intent.OnCompleteClick -> completeMission()
            is CompletedTournamentDetailContract.Intent.OnDeleteClick -> deleteMission()
            is CompletedTournamentDetailContract.Intent.OnDeleteDialogShowChange -> updateDeleteDialogShow(intent.show)
            is CompletedTournamentDetailContract.Intent.OnDeleteCompleteDialogShowChange -> updateDeleteCompleteDialogShow(intent.show)
            is CompletedTournamentDetailContract.Intent.OnSatisfactionBottomSheetShowChange -> updateSatisfactionShow(intent.show)
            is CompletedTournamentDetailContract.Intent.OnFeedbackBottomSheetShowChange -> updateFeedbackShow(intent.show)
            is CompletedTournamentDetailContract.Intent.OnSliderRatingChange -> updateSliderRating(intent.rating)
            is CompletedTournamentDetailContract.Intent.ToggleFeedbackOption -> toggleFeedbackOption(intent.option)
            is CompletedTournamentDetailContract.Intent.ClearFeedbackOption -> clearFeedbackOption()
            is CompletedTournamentDetailContract.Intent.SubmitSatisfaction -> submitSatisfaction()
            is CompletedTournamentDetailContract.Intent.LoadUserInfo -> loadUserInfo()
        }
    }

    fun loadTournamentInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
            when (val result =
                tournamentRepository.getCompletedTournamentDetail(currentState.tournamentId)) {
                is Result.Success -> {
                    reduce { it.copy(tournamentDetail = result.data) }
                }
                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar("완료된 무드매치 정보 조회에 실패했습니다.")
                    )
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            when (val result = userRepository.getUserPrivacyInfo()) {
                is Result.Success -> {
                    reduce { it.copy(nickname = result.data.name) }
                }

                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 정보 조회에 실패하였습니다."
                        )
                    )
                }
            }
        }
    }

    private fun loadMissionInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
            when (val result = missionRepository.getMissionDetail(currentState.userMissionId)) {
                is Result.Success -> {
                    reduce { it.copy(mission = result.data) }
                }

                is Result.Error -> {
                    sendEffect(CompletedTournamentDetailContract.SideEffect.ShowSnackbar("미션 정보 조회에 실패했습니다."))
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private fun updateTab(tab: CompletedTournamentTab) {
        reduce { it.copy(selectedTab = tab) }
    }

    private fun completeMission() {
        reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
        viewModelScope.launch {
            when (val result =
                missionRepository.completeMission(currentState.mission.userMissionId)) {
                is Result.Success -> {
                    reduce { it.copy(mission = result.data) }
                    updateSatisfactionShow(true)
                }

                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 완료 처리에 실패했어요."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private fun deleteMission() {
        reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
        viewModelScope.launch {
            when (val result =
                missionRepository.deleteMission(currentState.mission.userMissionId)) {
                is Result.Success -> {
                    updateDeleteCompleteDialogShow(true)
                }

                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 삭제 처리에 실패했어요."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private fun submitSatisfaction() {
        reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
        viewModelScope.launch {
            when (val result = missionRepository.submitSatisfaction(
                currentState.mission.userMissionId,
                currentState.slidingRating,
                currentState.selectedFeedback.map { it.content }
            )) {
                is Result.Success -> {
                    makeReport()
                    sendEffect(CompletedTournamentDetailContract.SideEffect.NavigateToReportReady)
                }

                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "만족도 제출에 실패했어요."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private suspend fun makeReport() {
        reduce { it.copy(isLoading = MissionDetailLoadingType.REPORT) }
        delay(3000L)
        reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
    }

    private fun updateSatisfactionShow(show: Boolean) {
        reduce { it.copy(showSatisfactionBottomSheet = show) }
    }

    private fun updateFeedbackShow(show: Boolean) {
        reduce { it.copy(showFeedbackBottomSheet = show) }
    }

    private fun updateSliderRating(rating: Float) {
        reduce { it.copy(slidingRating = rating) }
    }

    private fun toggleFeedbackOption(option: FeedbackOption) {
        val currentList = currentState.selectedFeedback
        val updatedList = if (currentList.contains(option)) {
            currentList - option
        } else {
            currentList + option
        }
        reduce { it.copy(selectedFeedback = updatedList) }
    }

    private fun clearFeedbackOption() {
        reduce { it.copy(selectedFeedback = emptyList()) }
    }

    private fun updateDeleteDialogShow(show: Boolean) {
        reduce { it.copy(showDeleteDialog = show) }
    }

    private fun updateDeleteCompleteDialogShow(show: Boolean) {
        reduce { it.copy(showDeleteCompleteDialog = show) }
    }

    companion object {
        private fun loadFeedbackOptions(): List<FeedbackOption> =
            listOf(
                FeedbackOption("이 스타일이 저랑 안 맞았어요"),
                FeedbackOption("경험해보니 생각과 달랐어요"),
                FeedbackOption("선택 이유와 안 맞는 미션이었어요"),
                FeedbackOption("수행하기 막막한 미션이었어요")
            )
    }
}