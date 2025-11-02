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
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel for the [ProfileScreen].
 *
 * Responsible for loading the profile data (Patient or Doctor) of the
 * currently authenticated user and handling the logout logic.
 *
 * @param getCurrentUserIdUseCase Use case to get the ID of the logged-in user.
 * @param getPatientByIdUseCase Use case to fetch a patient by ID.
 * @param getDoctorByIdUseCase Use case to fetch a doctor by ID.
 * @param logoutUseCase Use case to log the user out.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    // Holds the current UI state for the profile screen [ProfileUiState].
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    /**
     * Loads the profile of the currently authenticated user.
     *
     * It first gets the user ID, then fetches either [Patient] or [Doctor] data
     * based on the [isDoctor] flag. Updates the [_uiState] to
     * [ProfileUiState.Loading], [ProfileUiState.Success], or [ProfileUiState.Error].
     *
     * @param isDoctor True if the logged-in user is a doctor, false if they are a patient.
     */
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
//                    _uiState.value = ProfileUiState.Error("User not found")
                    // Fallback to mock data if user is not found (temporary/debug logic)
                    _uiState.value = if(isDoctor) {
                        ProfileUiState.Success(Doctor(
                            id = "some-firebase-uid-123",
                            name = "Dr. Ana Silva",
                            crm = "12345",
                            uf = "SP",
                            specialization = "Cardiologia",
                            email = "ana@gmailzao.com",
                            cpf = "11111111111",
                            passwordHash = "123@abc",
                            rqe = "21212121"
                        ))
                    }else{
                        ProfileUiState.Success(Patient(
                            id = "some-firebase-uid-456",
                            name = "Pedro",
                            email = "pedro@gmail.com",
                            cpf = "12345678910",
                            passwordHash = "123@abc",
                            birthDate = LocalDate.parse("2000-01-01")
                        ))
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Executes the logout use case to sign the user out.
     */
    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}