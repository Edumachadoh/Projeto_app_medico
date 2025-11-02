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

/**
 * PEDRO:
 * ViewModel for the authentication screens [LoginScreen] and [RegisterScreen].
 *
 * Responsible for managing the UI state [AuthUiState] and coordinating
 * login and registration actions for patients and doctors, delegating
 * business logic to the corresponding UseCases.
 *
 * @param registerPatientUseCase Use case to register a new patient.
 * @param registerDoctorUseCase Use case to register a new doctor.
 * @param loginUseCase Use case to authenticate an existing user.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val registerDoctorUseCase: RegisterDoctorUseCase,
    private val loginUseCase: LoginUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    /**
     * PEDRO:
     * Attempts to authenticate a user with the provided email and password.
     * Updates [_authState] to [AuthUiState.Loading], then to
     * [AuthUiState.Success] or [AuthUiState.Error].
     *
     * @param email The user's email.
     * @param password The user's password.
     */
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

    /**
     * PEDRO:
     * Registers a new patient in the system.
     * Updates [_authState] to [AuthUiState.Loading], then to
     * [AuthUiState.Success] or [AuthUiState.Error].
     *
     * @param patient The [Patient] object with the new patient's data.
     */
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

    /**
     * PEDRO:
     * Registers a new doctor in the system.
     * This operation is executed on an IO thread ([Dispatchers.IO]).
     * Updates [_authState] to [AuthUiState.Loading], then to
     * [AuthUiState.Success] or [AuthUiState.Error].
     *
     * @param doctor The [Doctor] object with the new doctor's data.
     */
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