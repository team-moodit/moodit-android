package com.swyp.moodit.tournament.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.matchUp.MatchUpContract
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository,
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<TournamentResultContract.State, TournamentResultContract.Intent, TournamentResultContract.SideEffect>(
        initialState = TournamentResultContract.State()
    ) {

    private val matchId =
        savedStateHandle.toRoute<TournamentRoute.Result>().matchId

    init {
        observeNickname()
    }

    override fun handleIntents(intent: TournamentResultContract.Intent) {
        when (intent) {
            is TournamentResultContract.Intent.OnMissionDetailClick -> {
                val userMissionId = currentState.userMissionId
                if (userMissionId == 0L) {
                    approveMission()
                } else {
                    navigateToMissionDetail()
                }
            }

            is TournamentResultContract.Intent.OnMissionSelect -> {
                reduce {
                    val selectedMission = if (it.selectedMission?.id == intent.mission.id) {
                        null
                    } else {
                        intent.mission
                    }
                    it.copy(selectedMission = selectedMission)
                }
            }

            is TournamentResultContract.Intent.LoadResult -> {
                loadMatchResult()
            }

            is TournamentResultContract.Intent.OnExitClick -> {
                handleExitClick()
            }

            is TournamentResultContract.Intent.LoadUserInfo -> {
                loadUserInfo()
            }
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            userRepository.nickname.collect { nickname ->
                reduce { it.copy(nickname = nickname) }
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            when (val result = userRepository.getUserPrivacyInfo()) {
                is Result.Success -> {
                    reduce { it.copy(nickname = result.data.name) }
                }

                is Result.Error -> {
                    sendEffect(
                        TournamentResultContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 정보 조회에 실패하였습니다."
                        )
                    )
                }
            }
        }
    }

    fun loadMatchResult() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = missionRepository.getMissionOffers(matchId)) {
                is Result.Success -> {
                    val autoSetMission = result.data.missionSuggestions[0]
                    reduce { it.copy(moodMatchResult = result.data, selectedMission = autoSetMission) }
                }

                is Result.Error -> {
                    sendEffect(
                        TournamentResultContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "매치 정보를 결과를 조회할 수 없습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun approveMission() {
        val selectedMission = currentState.selectedMission ?: return
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = missionRepository.acceptMissionOffer(
                currentState.moodMatchResult.offerId,
                selectedMission.id
            )) {
                is Result.Success -> {
                    reduce { it.copy(userMissionId = result.data) }
                    tournamentRepository.clearOnGoingMatchUpResultId()
                    tournamentRepository.clearOnGoingTournamentId()
                    navigateToMissionDetail()
                }

                is Result.Error -> {
                    sendEffect(
                        TournamentResultContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "미션 수락에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun navigateToMissionDetail() {
        sendEffect(
            TournamentResultContract.SideEffect.NavigateToMissionDetail(
                currentState.userMissionId,
                MissionStatus.CREATED
            )
        )
    }

    private fun handleExitClick() {
        sendEffect(TournamentResultContract.SideEffect.NavigateToHome)
    }
}