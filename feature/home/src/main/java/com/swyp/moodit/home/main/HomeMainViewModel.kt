package com.swyp.moodit.home.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val missionRepository: MissionRepository
) :
    BaseViewModel<HomeMainContract.State, HomeMainContract.Intent, HomeMainContract.SideEffect>(
        initialState = HomeMainContract.State()
    ) {

    init {
        loadMissions()
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
        }
    }
}