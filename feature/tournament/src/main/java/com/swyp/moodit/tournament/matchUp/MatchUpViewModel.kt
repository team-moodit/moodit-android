package com.swyp.moodit.tournament.matchUp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

@HiltViewModel
class MatchUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MatchUpContract.State, MatchUpContract.Intent, MatchUpContract.SideEffect>(
        initialState = MatchUpContract.State()
    ) {
        private val tournamentId = savedStateHandle.toRoute<TournamentRoute.MatchUp>().tournamentId

    private val currentRoundQueue: Queue<MatchUp> = LinkedList()
    private val nextRoundWinner = mutableListOf<TasteCandidate>()
    private var totalMatchUpInCurrentRound = 0
    private var currentMatchIndex = 0
    private var currentRoundItemCount = 0

    init {
        val mockData = List(9) { i -> TasteCandidate(i.toLong(), "", "취향 사진 $i") }
        startTournament(mockData)
    }

    override fun handleIntents(intent: MatchUpContract.Intent) {
        when (intent) {
            is MatchUpContract.Intent.OnCandidateSelect -> {
                handleCandidateSelect(intent.candidate)
            }

            is MatchUpContract.Intent.OnReasonSelect -> {
                handleReasonSelect(intent.reasonId)
            }

            is MatchUpContract.Intent.OnBackStepClick -> {
                handleBackStepClick()
            }
        }
    }

    private fun handleCandidateSelect(tasteCandidate: TasteCandidate) {
        val isWalkOver = uiState.value.currentMatchUp?.candidateB == null
        if (isWalkOver) {
            nextRoundWinner.add(tasteCandidate)
            showNextMatch()
        } else {
            reduce { it.copy(currentStep = TournamentStep.REASON, selectedWinner = tasteCandidate) }
        }
    }

    private fun handleReasonSelect(reasonId: Long) {
        val winner = uiState.value.selectedWinner ?: return
        nextRoundWinner.add(winner)
        showNextMatch()
    }

    private fun handleBackStepClick() {
        sendEffect(MatchUpContract.SideEffect.NavigateBack)
    }

    private fun startTournament(photoList: List<TasteCandidate>) {
        if (photoList.size == 1) {
            sendEffect(MatchUpContract.SideEffect.NavigateToResult(photoList[0].id))
            return
        }
        currentRoundQueue.clear()
        nextRoundWinner.clear()
        currentMatchIndex = 0
        currentRoundItemCount = photoList.size

        for (i in photoList.indices step 2) {
            if (i + 1 < photoList.size) {
                currentRoundQueue.add(MatchUp(photoList[i], photoList[i + 1]))
            } else {
                currentRoundQueue.add(MatchUp(photoList[i], null))
            }
        }
        totalMatchUpInCurrentRound = currentRoundQueue.size
        showNextMatch()
    }

    private fun showNextMatch() {
        if (currentRoundQueue.isNotEmpty()) {
            val roundName = when (currentRoundItemCount) {
                2 -> "결승전"
                else -> "${currentRoundItemCount}강전"
            }
            val nextMatch = currentRoundQueue.poll()
            currentMatchIndex++

            reduce {
                it.copy(
                    currentRoundTitle = "$roundName ($currentMatchIndex/$totalMatchUpInCurrentRound)",
                    currentMatchUp = nextMatch,
                    currentStep = TournamentStep.MATCH_UP,
                    selectedWinner = null
                )
            }
        } else {
            startTournament(nextRoundWinner.toList())
        }
    }
}