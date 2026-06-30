package com.swyp.moodit.tournament.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository
) :
    BaseViewModel<TournamentResultContract.State, TournamentResultContract.Intent, TournamentResultContract.SideEffect>(
        initialState = TournamentResultContract.State()
    ) {

    private val matchResultId =
        savedStateHandle.toRoute<TournamentRoute.Result>().matchResultId

    override fun handleIntents(intent: TournamentResultContract.Intent) {
        when (intent) {
            is TournamentResultContract.Intent.OnMissionDetailClick -> {
                val userMissionId = currentState.userMissionId
                if (userMissionId == 0L) {
                    approveMission()
                } else {
                    sendEffect(
                        TournamentResultContract.SideEffect.NavigateToMissionDetail(
                            userMissionId,
                            MissionStatus.CREATED
                        )
                    )
                }
            }

            is TournamentResultContract.Intent.OnMissionSelect -> {
                reduce {
                    val selectedMission = if (it.selectedMission?.id == intent.mission.id) {
                        null
                    } else {
                        intent.mission
                    }
                    it.copy(selectedMission = selectedMission)
                }
            }

            is TournamentResultContract.Intent.LoadResult -> {
                loadMatchResult()
            }
        }
    }

    fun loadMatchResult() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = missionRepository.getMissionOffers(matchResultId)) {
                is Result.Success -> {
                    val assignedMissionId = result.data.assignedMissionId
                    if (assignedMissionId != 0L) reduce { it.copy(userMissionId = assignedMissionId) }
                    reduce { it.copy(moodMatchResult = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        TournamentResultContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "매치 정보를 결과를 조회할 수 없습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    fun approveMission() {
        val selectedMission = currentState.selectedMission ?: return
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = missionRepository.acceptMissionOffer(
                currentState.moodMatchResult.offerId,
                selectedMission.id
            )) {
                is Result.Success -> {
                    reduce { it.copy(userMissionId = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        TournamentResultContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 수락에 실패했습니다."
                        )
                    )
                }
            }
        }
    }
}