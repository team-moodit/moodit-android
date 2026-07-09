package com.swyp.moodit.tournament.inProgressDetail

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
class InProgressTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<InProgressTournamentDetailContract.State, InProgressTournamentDetailContract.Intent, InProgressTournamentDetailContract.SideEffect>(
        initialState = InProgressTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId
        )
    ) {

    private val tournamentId =
        savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId

    init {
        loadTournamentInfo()
    }

    override fun handleIntents(intent: InProgressTournamentDetailContract.Intent) {
        when (intent) {
            is InProgressTournamentDetailContract.Intent.OnDeleteTournamentClick -> {
                sendEffect(
                    InProgressTournamentDetailContract.SideEffect.ShowSnackbar("토너먼트 삭제하시겠습니까?")
                )
            }

            is InProgressTournamentDetailContract.Intent.OnResumeTournamentClick -> {
                sendEffect(
                    InProgressTournamentDetailContract.SideEffect.NavigateToMatchUp(tournamentId, false)
                )
            }
        }
    }

    fun loadTournamentInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = tournamentRepository.getInProgressTournamentDetail(tournamentId)) {
                is Result.Success -> {
                    reduce { it.copy(tournamentDetail = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        InProgressTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "진행 중인 토너먼트 상세 조회에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }
}