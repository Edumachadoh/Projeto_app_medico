package com.up.clinica_digital.di

import com.up.clinica_digital.domain.model.*
import com.up.clinica_digital.domain.repository.IAppointmentRepository
import com.up.clinica_digital.domain.repository.IDoctorRepository
import com.up.clinica_digital.domain.repository.IForumCommentRepository
import com.up.clinica_digital.domain.repository.IForumTopicRepository
import com.up.clinica_digital.domain.repository.IPatientRepository
import com.up.clinica_digital.domain.repository.IUserAuthRepository
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
    fun provideCreateDoctorUseCase(repo: IDoctorRepository) =
        CreateEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideUpdateDoctorUseCase(repo: IDoctorRepository) =
        UpdateEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideDeleteDoctorUseCase(repo: IDoctorRepository) =
        DeleteEntityUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideListDoctorsUseCase(repo: IDoctorRepository) =
        ListEntitiesUseCase<Doctor>(repo)

    @Provides @Singleton
    fun provideCreatePatientUseCase(repo: IPatientRepository) =
        CreateEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideUpdatePatientUseCase(repo: IPatientRepository) =
        UpdateEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideDeletePatientUseCase(repo: IPatientRepository) =
        DeleteEntityUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideListPatientsUseCase(repo: IPatientRepository) =
        ListEntitiesUseCase<Patient>(repo)

    @Provides @Singleton
    fun provideCreateAppointmentUseCase(repo: IAppointmentRepository) =
        CreateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideUpdateAppointmentUseCase(repo: IAppointmentRepository) =
        UpdateEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideDeleteAppointmentUseCase(repo: IAppointmentRepository) =
        DeleteEntityUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideListAppointmentsUseCase(repo: IAppointmentRepository) =
        ListEntitiesUseCase<Appointment>(repo)

    @Provides @Singleton
    fun provideCreateForumTopicUseCase(repo: IForumTopicRepository) =
        CreateEntityUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideListForumTopicsUseCase(repo: IForumTopicRepository) =
        ListEntitiesUseCase<ForumTopic>(repo)

    @Provides @Singleton
    fun provideCreateForumCommentUseCase(repo: IForumCommentRepository) =
        CreateEntityUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideListForumCommentsUseCase(repo: IForumCommentRepository) =
        ListEntitiesUseCase<ForumComment>(repo)

    @Provides @Singleton
    fun provideLoginUserUseCase(repo: IUserAuthRepository) =
        LoginUserUseCase(repo)

//    @Provides @Singleton
//    fun provideValidateCRMUseCase(repo: IDoctorRepository) =
//        ValidateCRMUseCase(repo)

    @Provides @Singleton
    fun provideListAppointmentsByDoctorUseCase(repo: IAppointmentRepository) =
        ListByDoctorUseCase(repo)

    @Provides @Singleton
    fun provideListAppointmentsByPatientUseCase(repo: IAppointmentRepository) =
        ListByPatientUseCase(repo)
}