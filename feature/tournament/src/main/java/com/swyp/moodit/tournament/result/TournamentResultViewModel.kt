package com.swyp.moodit.tournament.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
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

    private val winnerCandidateId =
        savedStateHandle.toRoute<TournamentRoute.Result>().winnerCandidateId

    init {
        Timber.d("$winnerCandidateId")
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
        }
    }

    fun loadResult() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(2000L)
            val result = "missionID123"
            reduce { it.copy(isLoading = false, missionId = result) }
        }
    }
}