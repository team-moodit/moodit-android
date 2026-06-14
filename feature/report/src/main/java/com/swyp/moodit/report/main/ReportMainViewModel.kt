package com.swyp.moodit.report.main

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportMainViewModel @Inject constructor() :
    BaseViewModel<ReportMainContract.State, ReportMainContract.Intent, ReportMainContract.SideEffect>(
        initialState = ReportMainContract.State()
    ) {

    override fun handleIntents(intent: ReportMainContract.Intent) {
        when (intent) {
            is ReportMainContract.Intent.SelectTab -> {
                updateTab(intent.tab)
            }
        }
    }

    private fun updateTab(tab: ReportTab) {
        reduce { it.copy(selectedTab = tab) }
    }
}