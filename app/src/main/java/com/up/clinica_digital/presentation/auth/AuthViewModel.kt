package com.up.clinica_digital.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.CreateEntityUseCase
import com.up.clinica_digital.domain.usecase.user.LoginUserUseCase
import com.up.clinica_digital.domain.usecase.user.ValidateDoctorCrmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerPatientUseCase: CreateEntityUseCase<Patient>,
    private val registerDoctorUseCase: CreateEntityUseCase<Doctor>,
    private val validateDoctorCrmUseCase: ValidateDoctorCrmUseCase,
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
                val success = registerPatientUseCase.invoke(patient)
                if (success) {
                    _authState.value = AuthUiState.Success(patient.id, patient.role)
                } else {
                    _authState.value = AuthUiState.Error("Falha ao cadastrar paciente")
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Erro no cadastro")
            }
        }
    }

    fun registerDoctor(doctor: Doctor) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val crmValidation = validateDoctorCrmUseCase.invoke(doctor.crm, doctor.uf)
                if (crmValidation.isEmpty()) {
                    _authState.value = AuthUiState.Error("CRM inválido")
                    return@launch
                }

                val success = registerDoctorUseCase.invoke(doctor)
                if (success) {
                    _authState.value = AuthUiState.Success(doctor.id, doctor.role)
                } else {
                    _authState.value = AuthUiState.Error("Falha ao cadastrar médico")
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Erro no cadastro do médico")
            }
        }
    }
}