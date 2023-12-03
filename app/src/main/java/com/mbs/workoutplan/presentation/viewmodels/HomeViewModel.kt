package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.domain.usecase.GetUserWorkoutsUseCase
import com.mbs.workoutplan.presentation.event.HomeEvent
import com.mbs.workoutplan.presentation.uistate.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserWorkoutsUseCase: GetUserWorkoutsUseCase
) : ViewModel() {

    init {
        getUserWorkouts()
    }

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asLiveData()

    private fun getUserWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUserWorkoutsUseCase.invoke()
                if (response.isNotEmpty()) {
                    _uiState.update { it.copy(workouts = response) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = HomeEvent.Error(e.message.toString())) }
            }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}