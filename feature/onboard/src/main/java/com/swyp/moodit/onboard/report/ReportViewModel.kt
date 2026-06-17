package com.swyp.moodit.onboard.report

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) :
    BaseViewModel<ReportContract.State, ReportContract.Intent, ReportContract.SideEffect>(
        initialState = ReportContract.State
    ) {
    override fun handleIntents(intent: ReportContract.Intent) {
        when (intent) {
            is ReportContract.Intent.OnStartClick -> {
                completeOnBoarding()
            }
        }
    }

    fun completeOnBoarding() {
        viewModelScope.launch {
            userPreferencesDataStore.setOnBoardingCompleted(true)
            sendEffect(ReportContract.SideEffect.NavigateToLogin)
        }
    }
}