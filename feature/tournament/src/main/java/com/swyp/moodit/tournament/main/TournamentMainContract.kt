package com.swyp.moodit.tournament.main

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import com.swyp.moodit.model.tournament.TournamentState
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentMainContract {
    data class State(
        val isLoading: Boolean = false,
        val inProgressTournaments: List<InProgressTournamentDetail> = listOf(
            InProgressTournamentDetail(
                id = 1L,
                title = "출근 할 때 입을 옷",
                currentRound = "8강",
                imageUris = listOf(
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1"
                )
            )
        ),
        val completedTournaments: List<CompletedTournamentDetail> = listOf(
            CompletedTournamentDetail(
                id = 1L,
                missionInfo = Mission(matchResult = MissionMatchResult(matchTitle = "봄에 입고 싶은 데일리룩"))
            ),
            CompletedTournamentDetail(
                id = 2L,
                missionInfo = Mission(matchResult = MissionMatchResult(matchTitle = "여름철 출근룩"))
            ),
            CompletedTournamentDetail(
                id = 3L,
                missionInfo = Mission(matchResult = MissionMatchResult(matchTitle = "약속있는 날 입을 옷"))
            ),
            CompletedTournamentDetail(
                id = 4L,
                missionInfo = Mission(matchResult = MissionMatchResult(matchTitle = "패션 아이템 모음"))
            ),
            CompletedTournamentDetail(
                id = 5L,
                missionInfo = Mission(matchResult = MissionMatchResult(matchTitle = "가을에 입고 싶은 데일리룩"))
            )
        )
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToTournamentDetail(
            val tournamentId: Long,
            val tournamentState: TournamentState
        ) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnTournamentClick(val tournamentId: Long, val tournamentState: TournamentState) :
            Intent
    }
}