package com.swyp.moodit.tournament.inProgressDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InProgressTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<InProgressTournamentDetailContract.State, InProgressTournamentDetailContract.Intent, InProgressTournamentDetailContract.SideEffect>(
        initialState = InProgressTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId
        )
    ) {

    private val tournamentId =
        savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId

    init {
        Timber.d("$tournamentId")
        loadTournamentInfo()
    }

    override fun handleIntents(intent: InProgressTournamentDetailContract.Intent) {
        when (intent) {
            is InProgressTournamentDetailContract.Intent.OnDeleteTournamentClick -> {
                sendEffect(
                    InProgressTournamentDetailContract.SideEffect.ShowSnackbar("토너먼트 삭제하시겠습니까?")
                )
            }
        }
    }

    fun loadTournamentInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            val isCompletedTournament = false

            reduce {
                it.copy(
                    isLoading = false,
                    tournamentId = tournamentId
                )
            }
        }
    }
}