package com.swyp.moodit.report.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository
) :
    BaseViewModel<ReportMainContract.State, ReportMainContract.Intent, ReportMainContract.SideEffect>(
        initialState = ReportMainContract.State()
    ) {
    init {
        getPagedReviewedMissions()
        observeNickname()
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
            is ReportMainContract.Intent.LoadUserInfo -> loadUserInfo()
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            userRepository.nickname.collect { nickname ->
                reduce { it.copy(nickname = nickname) }
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            when (val result = userRepository.getUserPrivacyInfo()) {
                is Result.Success -> {
                    reduce { it.copy(nickname = result.data.name) }
                }

                is Result.Error -> {
                    sendEffect(
                        ReportMainContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 정보 조회에 실패하였습니다."
                        )
                    )
                }
            }
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
                    reduce {
                        it.copy(
                            reportSummary = result.data,
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