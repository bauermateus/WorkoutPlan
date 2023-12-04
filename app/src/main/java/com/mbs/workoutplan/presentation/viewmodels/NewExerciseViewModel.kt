package com.mbs.workoutplan.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.domain.usecase.CreateNewExerciseUseCase
import com.mbs.workoutplan.domain.usecase.UploadPhotoUseCase
import com.mbs.workoutplan.presentation.event.NewExerciseEvent
import com.mbs.workoutplan.presentation.uistate.NewExerciseUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewExerciseViewModel(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val newExerciseUseCase: CreateNewExerciseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewExerciseUiState())
    val uiState = _uiState.asLiveData()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asLiveData()

    private suspend fun uploadImg(imgUri: Uri): String? {
        return viewModelScope.async(Dispatchers.IO) {
            try {
                uploadPhotoUseCase.invoke(imgUri)
            } catch (e: Exception) {
                _uiState.update { it.copy(event = NewExerciseEvent.Error(msg = e.message.toString())) }
                null
            }
        }.await()
    }

    fun createNewExercise(workoutId: String, name: String, obs: String?, imgUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            try {
                val imgUrl = imgUri?.let { uploadImg(it) }

                newExerciseUseCase.invoke(workoutId, ExerciseDTO(name, imgUrl, obs))

                _uiState.update { it.copy(event = NewExerciseEvent.CreatedSuccess) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = NewExerciseEvent.Error(e.message.toString())) }
            } finally {
                _loadingState.update { false }
            }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}