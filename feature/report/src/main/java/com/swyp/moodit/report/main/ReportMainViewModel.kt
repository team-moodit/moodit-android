package com.swyp.moodit.report.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val missionRepository: MissionRepository
) :
    BaseViewModel<ReportMainContract.State, ReportMainContract.Intent, ReportMainContract.SideEffect>(
        initialState = ReportMainContract.State()
    ) {
    init {
        getPagedReviewedMissions()
    }

    override fun handleIntents(intent: ReportMainContract.Intent) {
        when (intent) {
            is ReportMainContract.Intent.SelectTab -> {
                updateTab(intent.tab)
            }

            is ReportMainContract.Intent.OnSettingClick -> {
                sendEffect(ReportMainContract.SideEffect.NavigateToSetting)
            }

            is ReportMainContract.Intent.OnMissionClick -> {
                sendEffect(ReportMainContract.SideEffect.NavigateToMissionDetail(intent.missionId))
            }

            is ReportMainContract.Intent.LoadPreferenceReport -> {
                getPreferenceReport()
            }

            is ReportMainContract.Intent.OnCheckMissionClick -> sendEffect(ReportMainContract.SideEffect.NavigateToHome)
            is ReportMainContract.Intent.OnCreateMoodMatchClick -> sendEffect(ReportMainContract.SideEffect.NavigateToCreateMoodMatch)
        }
    }

    private fun updateTab(tab: ReportTab) {
        reduce { it.copy(selectedTab = tab) }
    }

    private fun getPreferenceReport() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = reportRepository.getPreferenceReport()) {
                is Result.Success -> {
                    val top3Distributions =
                        result.data.preferenceReport.distributions.sortedBy { it.percentage }
                            .takeLast(3)
                    reduce {
                        it.copy(
                            reportSummary = result.data,
                            top3Distributions = top3Distributions
                        )
                    }
                }

                is Result.Error -> {
                    sendEffect(
                        ReportMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "취향 리포트 조회에 실패했습니다.",
                            MooditSnackbarType.ERROR
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun getPagedReviewedMissions() {
        val feedbackSubMittedMissionsFlow =
            missionRepository.getPagingMissions(MissionState.REVIEWED)
                .cachedIn(viewModelScope)
        reduce { it.copy(feedbackSubMittedMissions = feedbackSubMittedMissionsFlow) }
    }
}