package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.data.db.models.WorkoutDTO
import com.mbs.workoutplan.domain.usecase.CreateWorkoutUseCase
import com.mbs.workoutplan.presentation.event.CreateWorkoutEvent
import com.mbs.workoutplan.presentation.uistate.CreateWorkoutUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateWorkoutViewModel(
    private val createWorkoutUseCase: CreateWorkoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateWorkoutUiState())
    val uiState = _uiState.asLiveData()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asLiveData()

    fun createWorkout(workout: WorkoutDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            try {
                val response = createWorkoutUseCase.invoke(workout)
                _uiState.update { it.copy(event = CreateWorkoutEvent.Success(id = response)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = CreateWorkoutEvent.Error(msg = e.message.toString())) }
            }
            _loadingState.update { false }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}