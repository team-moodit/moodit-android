package com.swyp.moodit.tournament.create

import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState
import java.util.Collections.emptyList

class CreateTournamentContract {
    data class State(
        val isLoading: Boolean = false,
        val showPhotoPicker: Boolean = true,
        val title: String = "",
        val selectedPhotos: List<SelectedPhoto> = emptyList(),
        val isTournamentValid: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToMatchUp(val tournamentId: Long) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnCreateTournamentClick : Intent
        data class OnPhotoPickerStateChange(val showPhotoPicker: Boolean) : Intent
        data class OnTitleChange(val title: String) : Intent
        data class OnPhotoChange(val photoUris: List<String>) : Intent
        data class OnDeletePhotoClick(val id: String) : Intent
        data class OnRetryPhotoUpload(val photo: SelectedPhoto) : Intent
    }
}