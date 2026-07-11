package com.swyp.moodit.home.missionDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.home.main.HomeMainContract
import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionDetailLoadingType
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository
) : BaseViewModel<MissionDetailContract.State, MissionDetailContract.Intent, MissionDetailContract.SideEffect>(
    initialState = MissionDetailContract.State(
        status = savedStateHandle.toRoute<HomeRoute.MissionDetail>().status,
        missionInfo = Mission(userMissionId = savedStateHandle.toRoute<HomeRoute.MissionDetail>().missionId),
        feedbackOptions = loadFeedbackOptions()
    )
) {
    init {
        observeNickname()
    }

    override fun handleIntents(intent: MissionDetailContract.Intent) {
        when (intent) {
            is MissionDetailContract.Intent.OnTryButtonClick -> {
                when (uiState.value.status) {
                    MissionStatus.CREATED -> sendEffect(MissionDetailContract.SideEffect.NavigateToHome)
                    MissionStatus.DEFAULT -> sendEffect(MissionDetailContract.SideEffect.NavigateToReportReady)
                }
            }

            is MissionDetailContract.Intent.OnCompleteClick -> completeMission()
            is MissionDetailContract.Intent.OnDeleteClick -> deleteMission()
            is MissionDetailContract.Intent.OnDeleteCompleteClick -> sendEffect(
                MissionDetailContract.SideEffect.NavigateToHome
            )

            is MissionDetailContract.Intent.LoadMissionDetail -> loadMissionDetail()
            is MissionDetailContract.Intent.OnFeedbackShowChange -> updateFeedbackShow(intent.show)
            is MissionDetailContract.Intent.OnSatisfactionShowChange -> updateSatisfactionShow(
                intent.show
            )

            is MissionDetailContract.Intent.OnDeleteDialogShowChange -> updateDeleteDialogShow(
                intent.show
            )

            is MissionDetailContract.Intent.OnDeleteCompleteDialogShowChange -> updateDeleteCompleteDialogShow(
                intent.show
            )

            is MissionDetailContract.Intent.OnSliderRatingChange -> updateSliderRating(intent.rating)
            is MissionDetailContract.Intent.ToggleFeedbackOption -> toggleFeedbackOption(intent.option)
            is MissionDetailContract.Intent.SubmitSatisfaction -> submitSatisfaction()
            is MissionDetailContract.Intent.ClearFeedbackOption -> clearFeedbackOption()
            is MissionDetailContract.Intent.LoadUserInfo -> loadUserInfo()
        }
    }

    private fun loadMissionDetail() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
            when (val result =
                missionRepository.getMissionDetail(currentState.missionInfo.userMissionId)) {
                is Result.Success -> {
                    reduce { it.copy(missionInfo = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        MissionDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 상세 조회에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = MissionDetailLoadingType.NONE) }
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            userRepository.nickname.collect { nickname ->
                reduce { it.copy(nickname = nickname) }
            }
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
                        MissionDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 정보 조회에 실패하였습니다."
                        )
                    )
                }
            }
        }
    }

    private fun completeMission() {
        reduce { it.copy(isLoading = MissionDetailLoadingType.DEFAULT) }
        viewModelScope.launch {
            when (val result =
                missionRepository.completeMission(currentState.missionInfo.userMissionId)) {
                is Result.Success -> {
                    reduce { it.copy(missionInfo = result.data) }
                    updateSatisfactionShow(true)
                }

                is Result.Error -> {
                    sendEffect(
                        MissionDetailContract.SideEffect.ShowSnackbar(
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
                missionRepository.deleteMission(currentState.missionInfo.userMissionId)) {
                is Result.Success -> {
                    updateDeleteCompleteDialogShow(true)
                }

                is Result.Error -> {
                    sendEffect(
                        MissionDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 삭제 처리에 실패했어요.", MooditSnackbarType.ERROR
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
                currentState.missionInfo.userMissionId,
                currentState.currentSliderRating,
                currentState.selectedFeedback.map { it.content }
            )) {
                is Result.Success -> {
                    makeReport()
                    sendEffect(MissionDetailContract.SideEffect.NavigateToReportReady)
                }

                is Result.Error -> {
                    sendEffect(
                        MissionDetailContract.SideEffect.ShowSnackbar(
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
        reduce { it.copy(currentSliderRating = rating) }
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