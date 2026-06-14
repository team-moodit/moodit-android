package com.swyp.moodit.tournament.result

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<TournamentResultContract.State, TournamentResultContract.Intent, TournamentResultContract.SideEffect>(
        initialState = TournamentResultContract.State()
    ) {

    private val winnerCandidateId =
        savedStateHandle.toRoute<TournamentRoute.Result>().winnerCandidateId

    init {
        Timber.d("$winnerCandidateId")
    }

    override fun handleIntents(intent: TournamentResultContract.Intent) {

    }
}