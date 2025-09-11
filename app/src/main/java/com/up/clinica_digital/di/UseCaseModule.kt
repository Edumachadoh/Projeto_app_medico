package com.up.clinica_digital.di

import com.up.clinica_digital.domain.model.*
import com.up.clinica_digital.domain.repository.AppointmentRepository
import com.up.clinica_digital.domain.repository.CfmRepository
import com.up.clinica_digital.domain.repository.DoctorRepository
import com.up.clinica_digital.domain.repository.ForumCommentRepository
import com.up.clinica_digital.domain.repository.ForumTopicRepository
import com.up.clinica_digital.domain.repository.PatientRepository
import com.up.clinica_digital.domain.repository.UserAuthRepository
import com.up.clinica_digital.domain.usecase.*
import com.up.clinica_digital.domain.usecase.user.*
import com.up.clinica_digital.domain.usecase.appointment.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides @Singleton
    fun provideCreateDoctorUseCase(repo: DoctorRepository) =
        CreateEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideUpdateDoctorUseCase(repo: DoctorRepository) =
        UpdateEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideDeleteDoctorUseCase(repo: DoctorRepository) =
        DeleteEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideListDoctorsUseCase(repo: DoctorRepository) =
        ListEntitiesUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideCreatePatientUseCase(repo: PatientRepository) =
        CreateEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideUpdatePatientUseCase(repo: PatientRepository) =
        UpdateEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideDeletePatientUseCase(repo: PatientRepository) =
        DeleteEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideListPatientsUseCase(repo: PatientRepository) =
        ListEntitiesUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideCreateAppointmentUseCase(repo: AppointmentRepository) =
        CreateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideUpdateAppointmentUseCase(repo: AppointmentRepository) =
        UpdateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideDeleteAppointmentUseCase(repo: AppointmentRepository) =
        DeleteEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideListAppointmentsUseCase(repo: AppointmentRepository) =
        ListEntitiesUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideCreateForumTopicUseCase(repo: ForumTopicRepository) =
        CreateEntityUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideListForumTopicsUseCase(repo: ForumTopicRepository) =
        ListEntitiesUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideCreateForumCommentUseCase(repo: ForumCommentRepository) =
        CreateEntityUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideListForumCommentsUseCase(repo: ForumCommentRepository) =
        ListEntitiesUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideLoginUserUseCase(repo: UserAuthRepository) =
        LoginUserUseCase(repo)

    @Provides
    @Singleton
    fun provideValidateDoctorCrmUseCase(repo: CfmRepository) =
        ValidateDoctorCrmUseCase(repo)

    @Provides @Singleton
    fun provideListAppointmentsByDoctorUseCase(repo: AppointmentRepository) =
        ListByDoctorUseCase(repo)

    @Provides @Singleton
    fun provideListAppointmentsByPatientUseCase(repo: AppointmentRepository) =
        ListByPatientUseCase(repo)
}