package com.swyp.moodit.tournament.matchUp

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val nextRoundWinner = mutableListOf<MoodCandidate>()
    private var totalMatchUpInCurrentRound = 0
    private var currentMatchIndex = 0
    private var currentRoundItemCount = 0

    init {
        // 초기 정보 가져오기 API
        val mockData =
            List(9) { i -> MoodCandidate(i.toLong(), "https://picsum.photos/200/300", "취향 사진 $i") }
        val mockReasons = listOf(
            MoodReason(id = 1L, content = "나한테 잘 어울릴 것 같아서"),
            MoodReason(id = 2L, content = "내 평소 분위기와 더 비슷해서"),
            MoodReason(id = 3L, content = "색감이 더 마음에 들어서"),
            MoodReason(id = 4L, content = "유행을 타지 않을 것 같아서")
        )
        reduce { it.copy(reasons = mockReasons) }
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
        }
    }

    private fun handleCandidateSelect(moodCandidate: MoodCandidate) {
        val isWalkOver = uiState.value.currentMatchUp?.candidateB == null
        if (isWalkOver) {
            nextRoundWinner.add(moodCandidate)
            showNextMatch()
        } else {
            reduce { it.copy(currentStep = TournamentStep.REASON, selectedWinner = moodCandidate) }
        }
    }

    private fun handleReasonSelect(reasonId: Long) {
        reduce { state ->
            val updatedReason = state.reasons.map { reason ->
                if (reason.id == reasonId) reason.copy(isSelected = !reason.isSelected)
                else reason.copy(isSelected = false)
            }
            val currentSelected = updatedReason.find { it.isSelected }
            state.copy(reasons = updatedReason, selectedReason = currentSelected)
        }
    }

    private fun handleRetryClick() {
        // 중간 저장 api 호출
        val saveMatchProgressResult = true
        if (saveMatchProgressResult) {
            updateRetryDialogState(false)
            sendEffect(MatchUpContract.SideEffect.ShowSnackbar("진행 상황이 저장됐어요."))
        }
        else {
            updateRetryDialogState(false)
            sendEffect(MatchUpContract.SideEffect.ShowSnackbar("저장에 실패했어요. 다시 시도해주세요.", MooditSnackbarType.ERROR))
        }
    }

    private fun handleExitClick() {
        updateRetryDialogState(false)
        sendEffect(MatchUpContract.SideEffect.NavigateBack)
    }

    private fun handleNextButtonClick() {
        // 중간 저장 API 호출
        // 성공 시 선택 이유, 선택 사진 상태 초기화

        val currentState = uiState.value
        val winner = currentState.selectedWinner ?: return

        nextRoundWinner.add(winner)

        reduce { state ->
            state.copy(
                selectedReason = null,
                reasons = state.reasons.map { it.copy(isSelected = false) }
            )
        }
        showNextMatch()
    }

    private fun startTournament(photoList: List<MoodCandidate>) {
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

            val isFinalMatch = currentRoundItemCount == 2 && currentRoundQueue.isEmpty()

            reduce {
                it.copy(
                    currentRoundTitle = roundName,
                    currentMatchUp = nextMatch,
                    currentStep = TournamentStep.MATCH_UP,
                    selectedWinner = null,
                    currentMatchIndex = currentMatchIndex,
                    totalMatchUpInCurrentRound = totalMatchUpInCurrentRound,
                    isMatchCompleted = isFinalMatch
                )
            }
            val nextMatchUpResult = true
            if (nextMatchUpResult) {
                sendEffect(MatchUpContract.SideEffect.ShowSnackbar("진행 상황이 저장됐어요"))
            } else {
                sendEffect(MatchUpContract.SideEffect.ShowSnackbar("진행 상황을 저장하지 못했어요", MooditSnackbarType.ERROR))
                sendIntent(MatchUpContract.Intent.ShowRetrySaveDialog)
            }
        } else {
            startTournament(nextRoundWinner.toList())
        }
    }

    private fun updateRetryDialogState(showRetryDialog: Boolean) {
        reduce { it.copy(showRetryDialog = showRetryDialog) }
    }
}