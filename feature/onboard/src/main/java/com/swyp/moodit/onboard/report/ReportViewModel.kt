package com.swyp.moodit.onboard.report

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor() :
    BaseViewModel<ReportContract.State, ReportContract.Intent, ReportContract.SideEffect>(
        initialState = ReportContract.State
    ) {
    override fun handleIntents(intent: ReportContract.Intent) {
        when (intent) {
            is ReportContract.Intent.OnStartClick -> {
                sendEffect(ReportContract.SideEffect.NavigateToLogin)
            }
        }
    }
}