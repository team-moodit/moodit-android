package com.swyp.moodit.tournament.inProgressDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.model.tournament.InProgressMatchState
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InProgressTournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<InProgressTournamentDetailContract.State, InProgressTournamentDetailContract.Intent, InProgressTournamentDetailContract.SideEffect>(
        initialState = InProgressTournamentDetailContract.State(
            tournamentId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().tournamentId,
            matchResultId = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().matchResultId,
            matchState = savedStateHandle.toRoute<TournamentRoute.InProgressDetail>().matchState
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
                deleteTournament()
            }

            is InProgressTournamentDetailContract.Intent.OnDeleteTournamentCompleteClick -> {
                sendEffect(
                    InProgressTournamentDetailContract.SideEffect.NavigateToTournament
                )
            }

            is InProgressTournamentDetailContract.Intent.OnResumeTournamentClick -> {
                if (currentState.matchResultId == -1L) {
                    sendEffect(
                        InProgressTournamentDetailContract.SideEffect.NavigateToMatchUp(
                            currentState.tournamentId,
                            false
                        )
                    )
                } else {
                    sendEffect(
                        InProgressTournamentDetailContract.SideEffect.NavigateToMoodMatchResult(
                            currentState.matchResultId
                        )
                    )
                }
            }

            is InProgressTournamentDetailContract.Intent.OnDeleteDialogShowChange -> {
                updateDeleteDialogShow(intent.show)
            }

            is InProgressTournamentDetailContract.Intent.OnDeleteCompleteDialogShowChange -> {
                updateDeleteCompleteDialogShow(intent.show)
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

    private fun deleteTournament() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = tournamentRepository.deleteTournament(tournamentId)) {
                is Result.Success -> {
                    updateDeleteCompleteDialogShow(true)
                }

                is Result.Error -> {
                    Timber.d(result.exception)
                    sendEffect(
                        InProgressTournamentDetailContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "토너먼트 삭제에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun updateDeleteDialogShow(show: Boolean) {
        reduce { it.copy(showDeleteDialog = show) }
    }

    private fun updateDeleteCompleteDialogShow(show: Boolean) {
        reduce { it.copy(showDeleteCompleteDialog = show) }
    }
}