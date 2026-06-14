package com.swyp.moodit.tournament.create

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTournamentViewModel @Inject constructor() :
    BaseViewModel<CreateTournamentContract.State, CreateTournamentContract.Intent, CreateTournamentContract.SideEffect>(
        initialState = CreateTournamentContract.State()
    ) {
    override fun handleIntents(intent: CreateTournamentContract.Intent) {
        when (intent) {
            CreateTournamentContract.Intent.OnCreateTournamentClick -> {
                createTournament()
            }
        }
    }

    private fun createTournament() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(2000L)
            val createTournamentResult = true
            if (createTournamentResult) {
                sendEffect(CreateTournamentContract.SideEffect.NavigateToMatchUp)
            } else {
                sendEffect(CreateTournamentContract.SideEffect.ShowSnackbar("토너먼트 생성에 실패했습니다."))
            }
            reduce { it.copy(isLoading = false) }
        }
    }
}