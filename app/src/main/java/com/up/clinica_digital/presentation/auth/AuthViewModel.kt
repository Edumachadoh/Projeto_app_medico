package com.up.clinica_digital.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.user.LoginUserUseCase
import com.up.clinica_digital.domain.usecase.user.RegisterDoctorUseCase
import com.up.clinica_digital.domain.usecase.user.RegisterPatientUseCase
import com.up.clinica_digital.domain.usecase.user.ValidateDoctorCrmUseCase
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
    private val validateDoctorCrmUseCase: ValidateDoctorCrmUseCase,
    private val loginUseCase: LoginUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    private val _crmValidationResult = MutableStateFlow<List<com.up.clinica_digital.domain.model.CfmDoctor>>(emptyList())
    val crmValidationResult: StateFlow<List<com.up.clinica_digital.domain.model.CfmDoctor>> = _crmValidationResult

    private val _crmValidationState = MutableStateFlow<CrmUiState>(CrmUiState.Idle)
    val crmValidationState: StateFlow<CrmUiState> = _crmValidationState

    fun validateDoctorCrm(crm: String, uf: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _crmValidationState.value = CrmUiState.Loading
            try {
                val result = validateDoctorCrmUseCase(crm, uf)
                if (result.isEmpty()) {
                    _crmValidationState.value = CrmUiState.Error("CRM não encontrado ou inválido")
                } else {
                    _crmValidationResult.value = result
                    _crmValidationState.value = CrmUiState.Success(result)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Erro ao validar CRM", e)
                _crmValidationState.value = CrmUiState.Error("Erro ao validar CRM: ${e.message}")
            }
        }
    }

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
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val crmValidation = withContext(Dispatchers.IO) {
                    validateDoctorCrmUseCase(doctor.crm, doctor.uf)
                }

                if (crmValidation.isEmpty()) {
                    _authState.value = AuthUiState.Error("CRM inválido")
                    return@launch
                }

                val uid = withContext(Dispatchers.IO) {
                    registerDoctorUseCase.invoke(doctor)
                }

                if (uid != null) {
                    _authState.value = AuthUiState.Success(uid, doctor.role)
                } else {
                    _authState.value = AuthUiState.Error("Falha ao cadastrar médico")
                }

            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "Erro no cadastro do médico")
            }
        }
    }
}