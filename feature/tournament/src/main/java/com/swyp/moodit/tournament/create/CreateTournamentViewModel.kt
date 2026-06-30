package com.swyp.moodit.tournament.create

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTournamentViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
) : BaseViewModel<CreateTournamentContract.State, CreateTournamentContract.Intent, CreateTournamentContract.SideEffect>(
    initialState = CreateTournamentContract.State()
) {
    override fun handleIntents(intent: CreateTournamentContract.Intent) {
        when (intent) {
            is CreateTournamentContract.Intent.OnCreateTournamentClick -> {
                createTournament()
            }

            is CreateTournamentContract.Intent.OnTitleChange -> {
                if (intent.title.length <= MAX_TITLE_LENGTH) {
                    reduce { it.copy(title = intent.title) }
                    checkCreateTournamentCondition()
                }
            }

            is CreateTournamentContract.Intent.OnPhotoChange -> {
                val existingUris = currentState.selectedPhotos.map { it.uri }.toSet()
                val uniqueNewUris = intent.photoUris.filter { it !in existingUris }
                val newPhotos =
                    uniqueNewUris.map { SelectedPhoto(uri = it, status = UploadStatus.Loading) }
                if (newPhotos.isNotEmpty()) {
                    reduce { it.copy(selectedPhotos = it.selectedPhotos + newPhotos) }
                    checkCreateTournamentCondition()
                    uploadPhotoParallel(newPhotos)
                }
            }

            is CreateTournamentContract.Intent.OnPhotoPickerStateChange -> {
                reduce { it.copy(showPhotoPicker = intent.showPhotoPicker) }
            }

            is CreateTournamentContract.Intent.OnDeletePhotoClick -> {
                reduce { currentState ->
                    val updatedPhotos =
                        currentState.selectedPhotos.filterNot { photo -> photo.id == intent.id }
                    currentState.copy(selectedPhotos = updatedPhotos)
                }
                checkCreateTournamentCondition()
            }

            is CreateTournamentContract.Intent.OnRetryPhotoUpload -> {
                retryPhotoUpload(intent.photo)

            }
        }
    }

    private fun createTournament() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            val serverIds = currentState.selectedPhotos.mapNotNull { it.serverId }
            when (val result =
                tournamentRepository.createMoodMatch(currentState.title, serverIds)) {
                is Result.Success -> {
                    sendEffect(CreateTournamentContract.SideEffect.NavigateToMatchUp(result.data, true))
                }

                is Result.Error -> {
                    sendEffect(
                        CreateTournamentContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "토너먼트 생성에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun uploadPhotoParallel(newPhotos: List<SelectedPhoto>) {
        viewModelScope.launch {
            newPhotos.forEach { photo ->
                launch {
                    when (val result = tournamentRepository.uploadImage(photo)) {
                        is Result.Success -> updateSinglePhotoState(result.data)
                        is Result.Error -> {
                            val errorMessage = result.exception.localizedMessage ?: "업로드 실패"
                            sendEffect(CreateTournamentContract.SideEffect.ShowSnackbar(errorMessage))
                            val errorPhoto = photo.copy(status = UploadStatus.Error(errorMessage))
                            updateSinglePhotoState(errorPhoto)
                        }
                    }
                }
            }
        }
    }

    private fun retryPhotoUpload(retryPhoto: SelectedPhoto) {
        val currentPhoto = retryPhoto.copy(status = UploadStatus.Loading)
        updateSinglePhotoState(currentPhoto)
        viewModelScope.launch {
            when (val retryUploadResult = tournamentRepository.uploadImage(currentPhoto)) {
                is Result.Success -> {
                    updateSinglePhotoState(retryUploadResult.data)
                }

                is Result.Error -> {
                    sendEffect(
                        CreateTournamentContract.SideEffect.ShowSnackbar(
                            retryUploadResult.exception.localizedMessage ?: "재시도 실패"
                        )
                    )
                    val error = currentPhoto.copy(
                        status = UploadStatus.Error(
                            retryUploadResult.exception.localizedMessage ?: "재시도 실패"
                        )
                    )
                    updateSinglePhotoState(error)
                }
            }
        }
    }

    private fun updateSinglePhotoState(updatedPhoto: SelectedPhoto) {
        reduce { state ->
            val updatedList = state.selectedPhotos.map { photo ->
                if (photo.id == updatedPhoto.id) updatedPhoto else photo
            }
            state.copy(selectedPhotos = updatedList)
        }
        checkCreateTournamentCondition()
    }

    private fun checkCreateTournamentCondition() {
        val isAllUploaded =
            currentState.selectedPhotos.isNotEmpty() && currentState.selectedPhotos.all { it.status is UploadStatus.Success }
        val photoCountCondition =
            currentState.selectedPhotos.size in MIN_PHOTO_COUNT..MAX_PHOTO_COUNT
        val titleCondition = currentState.title.length in MIN_TITLE_LENGTH..MAX_TITLE_LENGTH
        val createTournamentCondition = isAllUploaded && photoCountCondition && titleCondition
        reduce { it.copy(isTournamentValid = createTournamentCondition) }
    }

    companion object {
        const val MIN_PHOTO_COUNT = 1
        const val MAX_PHOTO_COUNT = 32
        const val MIN_TITLE_LENGTH = 1
        const val MAX_TITLE_LENGTH = 15
    }
}