package com.swyp.moodit.home.missionDetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MissionDetailContract.State, MissionDetailContract.Intent, MissionDetailContract.SideEffect>(
        initialState = MissionDetailContract.State()
    ) {
    val missionId = savedStateHandle.toRoute<HomeRoute.MissionDetail>().missionId
    val status = savedStateHandle.toRoute<HomeRoute.MissionDetail>().status

    init {
        Timber.d("missionId: $missionId")
        Timber.d("status: $status")
        reduce { it.copy(status = status) }
    }

    override fun handleIntents(intent: MissionDetailContract.Intent) {
        when (intent) {
            is MissionDetailContract.Intent.OnCompleteClick -> {
                when (uiState.value.status) {
                    MissionStatus.CREATED -> sendEffect(MissionDetailContract.SideEffect.NavigateToHome)
                    MissionStatus.DEFAULT -> sendEffect(MissionDetailContract.SideEffect.NavigateToReportReady)
                }
            }
        }
    }
}