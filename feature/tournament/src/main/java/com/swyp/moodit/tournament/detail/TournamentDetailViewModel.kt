package com.swyp.moodit.tournament.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<TournamentDetailContract.State, TournamentDetailContract.Intent, TournamentDetailContract.SideEffect>(
        initialState = TournamentDetailContract.State()
    ) {

    private val tournamentId =
        savedStateHandle.toRoute<TournamentRoute.Detail>().tournamentId

    init {
        Timber.d(tournamentId)
        loadTournamentInfo()
    }

    override fun handleIntents(intent: TournamentDetailContract.Intent) {
        when (intent) {
            is TournamentDetailContract.Intent.OnDeleteTournamentClick -> {
                sendEffect(
                    TournamentDetailContract.SideEffect.ShowSnackbar("토너먼트 삭제하시겠습니까?")
                )
            }
        }
    }

    fun loadTournamentInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(2000L)
            val isCompletedTournament = false

            reduce {
                it.copy(
                    isLoading = false,
                    tournamentId = tournamentId,
                    isCompleted = isCompletedTournament
                )
            }
        }
    }
}