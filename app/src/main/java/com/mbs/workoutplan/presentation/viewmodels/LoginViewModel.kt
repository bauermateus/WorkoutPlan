package com.mbs.workoutplan.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mbs.workoutplan.domain.usecase.SignInWithEmailAndPasswordUseCase
import com.mbs.workoutplan.presentation.event.LoginEvent
import com.mbs.workoutplan.presentation.uistate.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asLiveData()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asLiveData()

    fun validateEmail(email: String) {
        _uiState.update {
            it.copy(
                isEmailFilledCorrectly = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            )
        }
    }

    fun validatePassword(password: String) {
        _uiState.update {
            it.copy(
                isPasswordFilledCorrectly = password.length > 5
            )
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            val result = signInWithEmailAndPasswordUseCase.invoke(email, password)
            if (result.success) {
                _uiState.update {
                    it.copy(
                        event = LoginEvent.Success
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        event = LoginEvent.Error(result.error?.message.toString())
                    )
                }
            }
            _loadingState.update { false }
        }
    }

    fun clearEvents() {
        _uiState.update { it.copy(event = null) }
    }
}