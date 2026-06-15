package com.swyp.moodit.home.reportReady

import androidx.lifecycle.SavedStateHandle
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportReadyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<ReportReadyContract.State, ReportReadyContract.Intent, ReportReadyContract.SideEffect>(
        initialState = ReportReadyContract.State()
    ) {
    // 만족도 평가 후 서버로부터 생성된 Report Id 받아서 전달해야함. or 리포트 탭으로 전환되는지 확인해보자.

    override fun handleIntents(intent: ReportReadyContract.Intent) {
        when(intent) {
            is ReportReadyContract.Intent.OnNavigateReportClick -> sendEffect(ReportReadyContract.SideEffect.NavigateToReport)
            is ReportReadyContract.Intent.OnNavigateHomeClick -> sendEffect(ReportReadyContract.SideEffect.NavigateToHome)
        }
    }
}