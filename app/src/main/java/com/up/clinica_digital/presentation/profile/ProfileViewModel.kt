package com.up.clinica_digital.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.model.User
import com.up.clinica_digital.domain.model.UserRole
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getPatientByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getDoctorByIdUseCase: GetEntityByIdUseCase<Doctor>,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel(){
    private val _profileUiState = mutableStateOf<ProfileUiState>(ProfileUiState.Idle)
    val profileUiState: State<ProfileUiState> = _profileUiState

    fun loadProfile(userRole: UserRole){
        viewModelScope.launch {
            _profileUiState.value = ProfileUiState.Loading
            val userId = getCurrentUserIdUseCase.invoke()

            if(userId == null){
                _profileUiState.value = ProfileUiState.Error("Usuário não logado")
                return@launch
            }

            try{
                val  user: User? = when(userRole){
                    UserRole.PATIENT -> getDoctorByIdUseCase.invoke(userId)
                    UserRole.DOCTOR -> getPatientByIdUseCase.invoke(userId)
                }

                if (user != null){
                    _profileUiState.value = ProfileUiState.Success(user)
                }else{
                    _profileUiState.value = ProfileUiState.Error("Usuário não encontrado")
                }
            }catch (e: Exception){
                _profileUiState.value = ProfileUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}