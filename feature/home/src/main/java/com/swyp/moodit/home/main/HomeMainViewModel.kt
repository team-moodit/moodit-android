package com.swyp.moodit.home.main

import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor() :
    BaseViewModel<HomeMainContract.State, HomeMainContract.Intent, HomeMainContract.SideEffect>(
        initialState = HomeMainContract.State()
    ) {
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