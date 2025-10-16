package com.up.clinica_digital.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.user.LoginUserUseCase
import com.up.clinica_digital.domain.usecase.user.RegisterDoctorUseCase
import com.up.clinica_digital.domain.usecase.user.RegisterPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val registerDoctorUseCase: RegisterDoctorUseCase,
    private val loginUseCase: LoginUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val result = loginUseCase.invoke(email, password)
                if (result != null) {
                    _authState.value = AuthUiState.Success(
                        userId = result.userId,
                        role = result.role
                    )
                } else {
                    _authState.value = AuthUiState.Error("Credenciais inválidas")
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun registerPatient(patient: Patient) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val uid = registerPatientUseCase.invoke(patient)
                if (uid != null) {
                    _authState.value = AuthUiState.Success(uid, patient.role)
                } else {
                    _authState.value = AuthUiState.Error("Falha ao cadastrar paciente")
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Erro no cadastro")
            }
        }
    }

    fun registerDoctor(doctor: Doctor) {
        // ANA: We are specifying the context because we want to run this on a background thread.
        // This (Dispatchers.IO) is the ideal context for performing network requests or database operations.
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // ANA: UI State is updated on the main thread.
                withContext(Dispatchers.Main) {
                    _authState.value = AuthUiState.Loading
                }

                // ANA: Back-end register.
                val uid = registerDoctorUseCase.invoke(doctor)
                withContext(Dispatchers.Main) {
                    if (uid != null) {
                        _authState.value = AuthUiState.Success(uid, doctor.role)
                    } else {
                        _authState.value = AuthUiState.Error("Falha ao cadastrar médico")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthUiState.Error(e.message ?: "Erro no cadastro do médico")
                }
            }
        }
    }
}