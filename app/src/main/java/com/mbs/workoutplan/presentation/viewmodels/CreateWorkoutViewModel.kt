package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.data.db.models.Workout
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

    fun createWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = createWorkoutUseCase.invoke(workout)
                _uiState.update { it.copy(event = CreateWorkoutEvent.Success) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = CreateWorkoutEvent.Error(e.message.toString())) }
            }

        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}