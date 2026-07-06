package com.swyp.moodit.tournament.inProgressDetail

import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class InProgressTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val tournamentDetail: InProgressTournamentDetail = InProgressTournamentDetail(
            imageUris = listOf(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg",
                "https://example.com/image3.jpg",
                "https://example.com/image4.jpg",
                "https://example.com/image5.jpg",
                "https://example.com/image6.jpg",
                "https://example.com/image7.jpg",
                "https://example.com/image8.jpg",
                "https://example.com/image9.jpg",
                "https://example.com/image10.jpg",
                "https://example.com/image11.jpg",
                "https://example.com/image12.jpg",
                "https://example.com/image13.jpg",
                "https://example.com/image14.jpg",
                "https://example.com/image15.jpg",
                "https://example.com/image16.jpg"
            )
        )
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnDeleteTournamentClick : Intent
    }
}