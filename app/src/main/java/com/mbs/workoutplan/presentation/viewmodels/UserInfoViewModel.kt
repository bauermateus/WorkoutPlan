package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.domain.usecase.GetUserInfoUseCase
import com.mbs.workoutplan.presentation.event.ProfileEvent
import com.mbs.workoutplan.presentation.uistate.ProfileUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    init {
        getUserInfo()
    }

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asLiveData()

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getUserInfoUseCase.invoke()
                _uiState.update { it.copy(userData = response) }
            } catch (e: Exception) {
                _uiState.update { it.copy(event = ProfileEvent.Error(e.message.toString())) }
            }

        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}