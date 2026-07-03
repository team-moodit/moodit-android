package com.swyp.moodit.report.main

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) :
    BaseViewModel<ReportMainContract.State, ReportMainContract.Intent, ReportMainContract.SideEffect>(
        initialState = ReportMainContract.State()
    ) {
        init {
            getPreferenceReport()
        }

    override fun handleIntents(intent: ReportMainContract.Intent) {
        when (intent) {
            is ReportMainContract.Intent.SelectTab -> {
                updateTab(intent.tab)
            }

            is ReportMainContract.Intent.OnSettingClick -> {
                sendEffect(ReportMainContract.SideEffect.NavigateToSetting)
            }
        }
    }

    private fun updateTab(tab: ReportTab) {
        reduce { it.copy(selectedTab = tab) }
    }

    private fun getPreferenceReport() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when(val result = reportRepository.getPreferenceReport()) {
                is Result.Success -> {
                    reduce { it.copy(reportSummary = result.data) }
                }

                is Result.Error -> {
                    sendEffect(
                        ReportMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "취향 리포트 조회에 실패했습니다.", MooditSnackbarType.ERROR
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }
}