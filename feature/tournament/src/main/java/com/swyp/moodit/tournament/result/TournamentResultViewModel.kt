package com.swyp.moodit.tournament.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.model.Mission
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<TournamentResultContract.State, TournamentResultContract.Intent, TournamentResultContract.SideEffect>(
        initialState = TournamentResultContract.State()
    ) {

    private val matchResultId =
        savedStateHandle.toRoute<TournamentRoute.Result>().matchResultId

    init {
        Timber.d("$matchResultId")
        loadResult()
    }

    override fun handleIntents(intent: TournamentResultContract.Intent) {
        when (intent) {
            is TournamentResultContract.Intent.OnMissionDetailClick -> {
                sendEffect(
                    TournamentResultContract.SideEffect.NavigateToMissionDetail(
                        uiState.value.missionId,
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
        }
    }

    fun loadResult() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(2000L)
            val result = 123L
            val testMissions = listOf(
                Mission(123L, "title1", "내 체형에 적합한지 경험해보기", false),
                Mission(456L, "title2", "내 추구미에 부합하는지 확인해보기", false),
                Mission(789L, "title3", "내가 가진 아이템들과 어울리는지 확인해보기", false)
            )
            reduce { it.copy(isLoading = false, missionId = result, missions = testMissions) }
        }
    }
}