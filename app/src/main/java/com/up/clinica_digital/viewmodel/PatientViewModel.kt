package com.up.clinica_digital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.up.clinica_digital.dao.DAO_Patient
import com.up.clinica_digital.models.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PatientViewModel(private val patientDao: DAO_Patient) : ViewModel() {

    private val _patientList = MutableStateFlow<List<Patient>>(emptyList())
    val patientList: StateFlow<List<Patient>> = _patientList

    init {
        viewModelScope.launch {
            patientDao.getAllPatients().collect {
                _patientList.value = it
            }
        }
    }

    fun addPatient(patient: Patient) {
        viewModelScope.launch {
            patientDao.insertPatient(patient)
        }
    }


    fun getPatientById(id: String) {
        viewModelScope.launch {
            patientDao.getPatientById(id)
        }
    }

}