package com.swyp.moodit.home.missionDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.model.Mission
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val missionRepository: MissionRepository
) : BaseViewModel<MissionDetailContract.State, MissionDetailContract.Intent, MissionDetailContract.SideEffect>(
    initialState = MissionDetailContract.State(
        status = savedStateHandle.toRoute<HomeRoute.MissionDetail>().status,
        missionInfo = Mission(userMissionId = savedStateHandle.toRoute<HomeRoute.MissionDetail>().missionId),
    )
) {
    init {
        Timber.d("missionId: ${currentState.missionInfo.userMissionId}")
        Timber.d("status: ${currentState.status}")
    }

    override fun handleIntents(intent: MissionDetailContract.Intent) {
        when (intent) {
            is MissionDetailContract.Intent.OnCompleteClick -> {
                when (uiState.value.status) {
                    MissionStatus.CREATED -> sendEffect(MissionDetailContract.SideEffect.NavigateToHome)
                    MissionStatus.DEFAULT -> sendEffect(MissionDetailContract.SideEffect.NavigateToReportReady)
                }
            }

            is MissionDetailContract.Intent.LoadMissionDetail -> loadMissionDetail()
        }
    }

    private fun loadMissionDetail() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
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
            reduce { it.copy(isLoading = false) }
        }
    }
}