package com.swyp.moodit.home.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.AnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val missionRepository: MissionRepository,
    private val tournamentRepository: TournamentRepository,
    private val userRepository: UserRepository,
    private val analyticsHelper: AnalyticsHelper
) :
    BaseViewModel<HomeMainContract.State, HomeMainContract.Intent, HomeMainContract.SideEffect>(
        initialState = HomeMainContract.State()
    ) {

    private var isDialogShownInThisSession = false

    init {
        loadMissions()
        observeNickname()
    }

    private fun loadMissions() {
        val inProgressMissionsFlow = missionRepository.getPagingMissions(MissionState.IN_PROGRESS)
            .cachedIn(viewModelScope)
        val completedMissionsFlow = missionRepository.getPagingMissions(MissionState.COMPLETED)
            .cachedIn(viewModelScope)
        val feedbackSubMittedMissionsFlow =
            missionRepository.getPagingMissions(MissionState.REVIEWED)
                .cachedIn(viewModelScope)

        reduce {
            it.copy(
                inProgressMissions = inProgressMissionsFlow,
                completedMissions = completedMissionsFlow,
                feedbackSubMittedMissions = feedbackSubMittedMissionsFlow
            )
        }
    }

    override fun handleIntents(intent: HomeMainContract.Intent) {
        when (intent) {
            is HomeMainContract.Intent.OnSettingClick -> {
                sendEffect(HomeMainContract.SideEffect.NavigateToSetting)
            }

            is HomeMainContract.Intent.OnCreateTournamentClick -> {
                isDialogShownInThisSession = false
                sendEffect(HomeMainContract.SideEffect.NavigateToCreateTournament)
            }

            is HomeMainContract.Intent.OnMissionClick -> {
                sendEffect(
                    HomeMainContract.SideEffect.NavigateToMissionDetail(
                        intent.missionId,
                        MissionStatus.DEFAULT
                    )
                )
            }

            is HomeMainContract.Intent.OnDismissTournamentDialog -> {
                clearOnGoingTournamentId()
                clearMatchUpResultId()
            }

            is HomeMainContract.Intent.CheckOnGoingTournament -> {
                checkOnGoingTournament()
            }

            is HomeMainContract.Intent.OnResumeTournamentClick -> {
                isDialogShownInThisSession = false
                reduce { it.copy(showResumeTournamentDialog = false) }
                if (intent.tournamentId != -1L) {
                    val currentTimeStamp = System.currentTimeMillis()
                    analyticsHelper.logEvent(
                        AnalyticsEvent(
                            type = "tournament_resume",
                            extras = listOf(
                                Param("tournament_id", intent.tournamentId.toString()),
                                Param("resumed_at", currentTimeStamp.toString())
                            )
                        )
                    )
                    sendEffect(HomeMainContract.SideEffect.NavigateToMatchUp(intent.tournamentId))
                }
                if (intent.matchUpResultId != -1L)
                    sendEffect(HomeMainContract.SideEffect.NavigateToMatchResult(intent.tournamentId))
            }

            is HomeMainContract.Intent.LoadUserInfo -> {
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
                        HomeMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 정보 조회에 실패하였습니다."
                        )
                    )
                }
            }
        }
    }

    private fun checkOnGoingTournament() {
        if (isDialogShownInThisSession) return

        viewModelScope.launch {
            val tournamentId = tournamentRepository.onGoingTournamentId.firstOrNull() ?: -1L
            val matchUpResultId = tournamentRepository.onGoingMatchUpResultId.firstOrNull() ?: -1L
            if (tournamentId != -1L || matchUpResultId != -1L) {
                isDialogShownInThisSession = true
                reduce {
                    it.copy(
                        showResumeTournamentDialog = true,
                        resumeTournamentId = tournamentId,
                        resumeMatchUpResultId = matchUpResultId
                    )
                }
            }
        }
    }

    private fun clearOnGoingTournamentId() {
        reduce { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = tournamentRepository.clearOnGoingTournamentId()) {
                is Result.Success -> {
                    reduce { it.copy(showResumeTournamentDialog = false) }
                }

                is Result.Error -> {
                    sendEffect(
                        HomeMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "DataStore에 저장된 ID 삭제에 실패했어요."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun clearMatchUpResultId() {
        reduce { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = tournamentRepository.clearOnGoingMatchUpResultId()) {
                is Result.Success -> {
                    reduce { it.copy(showResumeTournamentDialog = false) }
                }

                is Result.Error -> {
                    sendEffect(
                        HomeMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "DataStore에 저장된 매치 결과 ID 삭제에 실패했어요."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }
}