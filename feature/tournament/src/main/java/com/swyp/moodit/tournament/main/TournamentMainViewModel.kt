package com.swyp.moodit.tournament.main

import androidx.compose.ui.test.isOn
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TournamentMainViewModel @Inject constructor() :
    BaseViewModel<TournamentMainContract.State, TournamentMainContract.Intent, TournamentMainContract.SideEffect>(
        initialState = TournamentMainContract.State()
    ) {

    override fun handleIntents(intent: TournamentMainContract.Intent) {
        when (intent) {
            is TournamentMainContract.Intent.OnInProgressTournamentClick -> {
                sendEffect(
                    TournamentMainContract.SideEffect.NavigateToInProgressTournamentDetail(
                        intent.tournamentId,
                    )
                )
            }

            is TournamentMainContract.Intent.OnCompletedTournamentClick -> {
                sendEffect(
                    TournamentMainContract.SideEffect.NavigateToCompletedTournamentDetail(intent.tournamentId)
                )
            }

            is TournamentMainContract.Intent.OnSettingClick -> {
                sendEffect(TournamentMainContract.SideEffect.NavigateToSetting)
            }
        }
    }
}