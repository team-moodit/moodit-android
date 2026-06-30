package com.swyp.moodit.tournament.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<TournamentResultContract.State, TournamentResultContract.Intent, TournamentResultContract.SideEffect>(
        initialState = TournamentResultContract.State()
    ) {

    private val matchResultId =
        savedStateHandle.toRoute<TournamentRoute.Result>().matchResultId

    override fun handleIntents(intent: TournamentResultContract.Intent) {
        when (intent) {
            is TournamentResultContract.Intent.OnMissionDetailClick -> {
                sendEffect(
                    TournamentResultContract.SideEffect.NavigateToMissionDetail(
                        uiState.value.userMissionId,
                        MissionStatus.CREATED
                    )
                )
            }

            is TournamentResultContract.Intent.OnMissionSelect -> {
                reduce {
                    val selectedMission = if (it.selectedMission == intent.missionId) {
                        null
                    } else {
                        intent.missionId
                    }
                    it.copy(selectedMission = selectedMission)
                }
            }

            is TournamentResultContract.Intent.LoadResult -> { loadMatchResult()}
        }
    }

    fun loadMatchResult() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = tournamentRepository.getMissionOffers(matchResultId)) {
                is Result.Success -> {
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
}