package com.up.clinica_digital.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.model.User
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import com.up.clinica_digital.domain.usecase.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile(isDoctor: Boolean) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val userId = getCurrentUserIdUseCase.invoke()

            if (userId == null) {
                _uiState.value = ProfileUiState.Error("User not authenticated")
                return@launch
            }

            try {
                val user: User? = if (isDoctor) {
                    getDoctorByIdUseCase.invoke(userId)
                } else {
                    getPatientByIdUseCase.invoke(userId)
                }

                if (user != null) {
                    _uiState.value = ProfileUiState.Success(user)
                } else {
                    _uiState.value = ProfileUiState.Error("User not found")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}