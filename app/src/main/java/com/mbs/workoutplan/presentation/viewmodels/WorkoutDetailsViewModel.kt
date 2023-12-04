package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.domain.usecase.DeleteExerciseUseCase
import com.mbs.workoutplan.domain.usecase.GetWorkoutDetailsUseCase
import com.mbs.workoutplan.presentation.event.WorkoutDetailsEvent
import com.mbs.workoutplan.presentation.uistate.WorkoutDetailsUiState
import com.mbs.workoutplan.presentation.views.fragments.WorkoutDetailsFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(
    private val getWorkoutDetailsUseCase: GetWorkoutDetailsUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id = WorkoutDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).workoutId

    private val _uiState = MutableStateFlow(WorkoutDetailsUiState())
    val uiState = _uiState.asLiveData()

    val currentUiState get() = _uiState.value

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asLiveData()

    fun getWorkoutInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            try {
                val response = getWorkoutDetailsUseCase.invoke(id)
                response?.let { workout ->
                    _uiState.update { it.copy(workout = workout) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = WorkoutDetailsEvent.Error(msg = e.message.toString())) }
            } finally {
                _loadingState.update { false }
            }
        }
    }

    fun deleteExercise(exercise: ExerciseDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteExerciseUseCase.invoke(id, exercise)
                _uiState.update { it.copy(event = WorkoutDetailsEvent.DeleteSuccess("Exercício deletado.")) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = WorkoutDetailsEvent.Error(e.message.toString())) }
            }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}