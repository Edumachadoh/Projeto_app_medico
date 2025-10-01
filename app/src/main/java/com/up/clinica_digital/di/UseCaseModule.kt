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
    //region DOCTOR
    @Provides @Singleton
    fun provideRegisterDoctorUseCase(repo: UserAuthRepository) =
        RegisterDoctorUseCase(repo)

    @Provides @Singleton
    fun provideGetDoctorByIdUseCase(repo: DoctorRepository) =
        GetEntityByIdUseCase<Doctor>(repo)
    @Provides @Singleton
    fun provideListDoctorsUseCase(repo: DoctorRepository) =
        ListEntitiesUseCase<Doctor>(repo)
    @Provides @Singleton
    fun provideUpdateDoctorUseCase(repo: DoctorRepository) =
        UpdateEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideDeleteDoctorUseCase(repo: DoctorRepository) =
        DeleteEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideValidateDoctorCrmUseCase(repo: CfmRepository) =
        ValidateDoctorCrmUseCase(repo)

    @Provides @Singleton
    fun provideListDoctorByUFAndSpecialityUseCase(repo: DoctorRepository) =
        ListDoctorByUFAndSpecialityUseCase(repo)
    //endregion DOCTOR

    //region PATIENT
    @Provides @Singleton
    fun provideRegisterPatientUseCase(repo: UserAuthRepository) =
        RegisterPatientUseCase(repo)

    @Provides @Singleton
    fun provideListPatientsUseCase(repo: PatientRepository) =
        ListEntitiesUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideGetPatientByIdUseCase(repo: PatientRepository) =
        GetEntityByIdUseCase<Patient>(repo)
    @Provides @Singleton
    fun provideUpdatePatientUseCase(repo: PatientRepository) =
        UpdateEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideDeletePatientUseCase(repo: PatientRepository) =
        DeleteEntityUseCase<Patient>(repo)
    //endregion PATIENT

    //region APPOINTMENT
    @Provides @Singleton
    fun provideCreateAppointmentUseCase(repo: AppointmentRepository) =
        CreateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideListAppointmentsUseCase(repo: AppointmentRepository) =
        ListEntitiesUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideGetAppointmentByIdUseCase(repo: AppointmentRepository) =
        GetEntityByIdUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideUpdateAppointmentUseCase(repo: AppointmentRepository) =
        UpdateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideDeleteAppointmentUseCase(repo: AppointmentRepository) =
        DeleteEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideListAppointmentsByDoctorUseCase(repo: AppointmentRepository) =
        ListByDoctorUseCase(repo)

    @Provides @Singleton
    fun provideListAppointmentsByPatientUseCase(repo: AppointmentRepository) =
        ListByPatientUseCase(repo)
    //endregion APPOINTMENT

    //region FORUM TOPIC
    @Provides @Singleton
    fun provideCreateForumTopicUseCase(repo: ForumTopicRepository) =
        CreateEntityUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideListForumTopicsUseCase(repo: ForumTopicRepository) =
        ListEntitiesUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideGetForumTopicByIdUseCase(repo: ForumTopicRepository) =
        GetEntityByIdUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideUpdateForumTopicUseCase(repo: ForumTopicRepository) =
        UpdateEntityUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideDeleteForumTopicUseCase(repo: ForumTopicRepository) =
        DeleteEntityUseCase<ForumTopic>(repo)
    //endregion FORUM TOPIC

    //region FORUM COMMENT
    @Provides @Singleton
    fun provideCreateForumCommentUseCase(repo: ForumCommentRepository) =
        CreateEntityUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideListForumCommentsUseCase(repo: ForumCommentRepository) =
        ListEntitiesUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideGetForumCommentByIdUseCase(repo: ForumCommentRepository) =
        GetEntityByIdUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideUpdateForumCommentUseCase(repo: ForumCommentRepository) =
        UpdateEntityUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideDeleteForumCommentUseCase(repo: ForumCommentRepository) =
        DeleteEntityUseCase<ForumComment>(repo)
    //endregion FORUM COMMENT

    //region LOGIN
    @Provides @Singleton
    fun provideLoginUserUseCase(repo: UserAuthRepository) =
        LoginUserUseCase(repo)

    @Provides @Singleton
    fun provideLogoutUseCase(repo: UserAuthRepository) =
        LogoutUseCase(repo)

    @Provides @Singleton
    fun provideGetCurrentUserIdUseCase(repo: UserAuthRepository) =
        GetCurrentUserIdUseCase(repo)
    //endregion LOGIN
}