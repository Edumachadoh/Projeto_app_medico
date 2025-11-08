package com.up.clinica_digital.presentation.home

import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.usecase.GetEntityByIdUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.User
import com.up.clinica_digital.domain.usecase.user.GetCurrentUserIdUseCase
import com.up.clinica_digital.domain.usecase.user.ListDoctorBySpecialityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserByIdUseCase: GetEntityByIdUseCase<Patient>,
    private val getDoctorsBySpecialtyUseCase: ListDoctorBySpecialityUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors

    fun loadHomeData(specialty: String = "Cardiologia") {
        viewModelScope.launch {
            val uid = getCurrentUserIdUseCase.invoke() ?: return@launch
            val userData = getUserByIdUseCase.invoke(uid)
            _user.value = userData

            val doctorsList = getDoctorsBySpecialtyUseCase.invoke(specialty)
            _doctors.value = doctorsList
        }
    }
}
