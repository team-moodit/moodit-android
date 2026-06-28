package com.swyp.moodit.tournament.matchUp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.Candidate
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MatchUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<MatchUpContract.State, MatchUpContract.Intent, MatchUpContract.SideEffect>(
        initialState = MatchUpContract.State(
            isStarted = savedStateHandle.toRoute<TournamentRoute.MatchUp>().isStarted
        )
    ) {
    private val tournamentId = savedStateHandle.toRoute<TournamentRoute.MatchUp>().tournamentId

    override fun handleIntents(intent: MatchUpContract.Intent) {
        when (intent) {
            is MatchUpContract.Intent.OnCandidateSelect -> {
                handleCandidateSelect(intent.candidate)
            }

            is MatchUpContract.Intent.OnReasonSelect -> {
                handleReasonSelect(intent.reasonId)
            }

            is MatchUpContract.Intent.OnNextButtonClick -> {
                handleNextButtonClick()
            }

            is MatchUpContract.Intent.ShowRetrySaveDialog -> {
                updateRetryDialogState(true)
            }

            is MatchUpContract.Intent.OnRetryClick -> {
                handleRetryClick()
            }

            is MatchUpContract.Intent.OnExitClick -> {
                handleExitClick()
            }

            is MatchUpContract.Intent.LoadMatchUpInfo -> {
                getTournamentInfo()
            }
        }
    }

    fun getTournamentInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            val result = if (currentState.isStarted) {
                tournamentRepository.getMatchUpInitInfo(tournamentId)
            } else {
                Timber.d("다음 매치 조회됨.")
                tournamentRepository.getMatchUpProgressInfo(tournamentId)
            }
            when (result) {
                is Result.Success -> {
                    val data = result.data
                    val progressFraction = if (data.totalRounds > 0) {
                        data.curMatchIndex.toFloat() / data.totalRounds
                    } else {
                        0f
                    }
                    reduce {
                        it.copy(
                            matchUpInfo = result.data,
                            progressFraction = progressFraction,
                            isStarted = false
                        )
                    }
                }

                is Result.Error -> {
                    sendEffect(
                        MatchUpContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "매치 정보를 조회할 수 없습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun handleCandidateSelect(moodCandidate: Candidate) {
        reduce { it.copy(currentStep = TournamentStep.REASON, selectedWinner = moodCandidate) }
    }

    private fun handleReasonSelect(reasonId: Long) {
        val isAlreadySelected = currentState.selectedReason?.id == reasonId
        val nextSelectedReason = if (isAlreadySelected) {
            null
        } else {
            currentState.matchUpInfo.reasons.find { it.id == reasonId }
        }
        reduce { it.copy(selectedReason = nextSelectedReason) }
    }

    private fun handleRetryClick() {
        saveMatchUp(
            onSuccess = {
                updateRetryDialogState(false)
                handleSaveMatchUpSuccess()
            },
            onError = {
                updateRetryDialogState(false)
                sendEffect(
                    MatchUpContract.SideEffect.ShowSnackbar(
                        "저장에 실패했어요. 다시 시도해주세요.",
                        MooditSnackbarType.ERROR
                    )
                )
            }
        )
    }

    private fun handleExitClick() {
        updateRetryDialogState(false)
        sendEffect(MatchUpContract.SideEffect.NavigateBack)
    }

    private fun handleNextButtonClick() {
        saveMatchUp(
            onSuccess = {
                handleSaveMatchUpSuccess()
            },
            onError = {
                sendEffect(
                    MatchUpContract.SideEffect.ShowSnackbar(
                        "진행 상황을 저장하지 못했어요",
                        MooditSnackbarType.ERROR
                    )
                )
                sendIntent(MatchUpContract.Intent.ShowRetrySaveDialog)
            }
        )
    }

    private fun saveMatchUp(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val winnerId = currentState.selectedWinner?.id ?: return
        val reasonId = currentState.selectedReason?.id ?: return

        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            val result = tournamentRepository.saveMatchUp(
                matchId = tournamentId,
                winnerId = winnerId,
                reasonId = reasonId
            )
            when (result) {
                is Result.Success -> onSuccess()
                is Result.Error -> onError(result.exception.message ?: "")
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun handleSaveMatchUpSuccess() {
        sendEffect(MatchUpContract.SideEffect.ShowSnackbar("진행 상황이 저장됐어요"))

        if (currentState.matchUpInfo.isCompleted) {
            sendEffect(
                MatchUpContract.SideEffect.NavigateToResult(
                    currentState.selectedWinner?.id ?: 0L
                )
            )
        } else {
            reduce {
                it.copy(
                    selectedReason = null,
                    selectedWinner = null,
                    currentStep = TournamentStep.MATCH_UP,
                    isLoading = true
                )
            }
            getTournamentInfo()
        }
    }

    private fun updateRetryDialogState(showRetryDialog: Boolean) {
        reduce { it.copy(showRetryDialog = showRetryDialog) }
    }
}