package com.swyp.moodit.tournament.completedDetail

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CompletedTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val tournamentDetail: CompletedTournamentDetail = CompletedTournamentDetail(
            imageUris = listOf(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16"
            ),
            missionInfo = Mission(
                matchResult = MissionMatchResult(
                    matchTitle = "무드매치",
                    matchCompletedAt = "2023-01-01"
                )
            )
        ),
        val selectedTab: CompletedTournamentTab = CompletedTournamentTab.MOOD_MATCH
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: CompletedTournamentTab) : Intent
    }
}

enum class CompletedTournamentTab(val tabName: String) {
    MOOD_MATCH("무드매치"), MISSION("미션")
}