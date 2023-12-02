package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.domain.usecase.GetUserInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    init {
        getUserInfo()
    }

    private val _uiState = MutableStateFlow("")
    val uiState = _uiState.asLiveData()

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserInfoUseCase.invoke()
            if (response.data != null && response.error == null) {
                _uiState.update { response.data.name }
            } else {
                //
            }
        }
    }

//    fun clearEvents() {
//        _uiState.update { it.copy(event = null) }
//    }
}