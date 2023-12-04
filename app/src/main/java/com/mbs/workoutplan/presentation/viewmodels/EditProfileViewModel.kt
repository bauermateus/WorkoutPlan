package com.mbs.workoutplan.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.data.db.models.UserDataDTO
import com.mbs.workoutplan.domain.usecase.UpdateUserInfoUseCase
import com.mbs.workoutplan.domain.usecase.UploadProfilePictureUseCase
import com.mbs.workoutplan.presentation.event.EditProfileEvent
import com.mbs.workoutplan.presentation.uistate.EditProfileUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val uploadProfilePictureUseCase: UploadProfilePictureUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asLiveData()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asLiveData()

    private suspend fun uploadImg(imgUri: Uri): String? {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                uploadProfilePictureUseCase.invoke(imgUri)
            } catch (e: Exception) {
                _uiState.update { it.copy(event = EditProfileEvent.Error(msg = e.message.toString())) }
                null
            }
        }.await()
    }

    fun updateData(name: String, imgUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            try {
                val imgUrl = imgUri?.let { uploadImg(it) }

                updateUserInfoUseCase.invoke(UserDataDTO(name, imgUrl))

                _uiState.update { it.copy(event = EditProfileEvent.Success("Dados atualizados")) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = EditProfileEvent.Error(e.message.toString())) }
            } finally {
                _loadingState.update { false }
            }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}