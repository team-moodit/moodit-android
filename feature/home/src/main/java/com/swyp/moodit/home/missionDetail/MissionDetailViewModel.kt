package com.swyp.moodit.home.missionDetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.swyp.moodit.navigation.HomeRoute
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

    init {
        Timber.d("missionId: $missionId")
    }
    override fun handleIntents(intent: MissionDetailContract.Intent) {
        TODO("Not yet implemented")
    }
}