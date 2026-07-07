package com.swyp.moodit.tournament.completedDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<CompletedTournamentDetailContract.State, CompletedTournamentDetailContract.Intent, CompletedTournamentDetailContract.SideEffect>(
        initialState = CompletedTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId
        )
    ) {

    private val tournamentId =
        savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId

    init {
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
            when (val result = tournamentRepository.getCompletedTournamentDetail(tournamentId)) {
                is Result.Success -> {
                    reduce { it.copy(tournamentDetail = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        CompletedTournamentDetailContract.SideEffect.ShowSnackbar("완료된 무드매치 정보 조회에 실패했습니다.")
                    )
                }
            }

            reduce { it.copy(isLoading = false) }
        }
    }

    private fun updateTab(tab: CompletedTournamentTab) {
        reduce { it.copy(selectedTab = tab) }
    }
}