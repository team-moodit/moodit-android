package com.swyp.moodit.tournament.completedDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.inProgressDetail.InProgressTournamentDetailContract
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CompletedTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<CompletedTournamentDetailContract.State, CompletedTournamentDetailContract.Intent, CompletedTournamentDetailContract.SideEffect>(
        initialState = CompletedTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId
        )
    ) {

    private val tournamentId =
        savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId

    init {
        Timber.d("$tournamentId")
        loadTournamentInfo()
    }

    override fun handleIntents(intent: CompletedTournamentDetailContract.Intent) {
        when (intent) {
            is CompletedTournamentDetailContract.Intent.SelectTab -> updateTab(intent.tab)
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

    private fun updateTab(tab: CompletedTournamentTab) {
        reduce { it.copy(selectedTab = tab) }
    }
}