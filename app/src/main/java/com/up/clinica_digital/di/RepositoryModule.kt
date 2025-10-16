package com.up.clinica_digital.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.data.remote.datasource.CfmRemoteDataSource
import com.up.clinica_digital.data.repository.*
import com.up.clinica_digital.domain.repository.AppointmentRepository
import com.up.clinica_digital.domain.repository.CfmRepository
import com.up.clinica_digital.domain.repository.DoctorRepository
import com.up.clinica_digital.domain.repository.ForumCommentRepository
import com.up.clinica_digital.domain.repository.ForumTopicRepository
import com.up.clinica_digital.domain.repository.PatientRepository
import com.up.clinica_digital.domain.repository.UserAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// ANA: DI delivers data repositories to use cases and view models. This module acts as a bridge,
// providing the data layer to the business logic layer so they can work together. Most of these are
// just specifications of our generic repository (SEE USE CASE MODULE TO UNDERSTAND GENERICS BETTER.)
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserAuthRepository = FirebaseUserAuthRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun provideDoctorRepository(
        firestore: FirebaseFirestore
    ): DoctorRepository = FirebaseDoctorRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun providePatientRepository(
        firestore: FirebaseFirestore
    ): PatientRepository = FirebasePatientRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        firestore: FirebaseFirestore
    ): AppointmentRepository = FirebaseAppointmentRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideForumTopicRepository(
        firestore: FirebaseFirestore
    ): ForumTopicRepository = FirebaseForumTopicRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideForumCommentRepository(
        firestore: FirebaseFirestore
    ): ForumCommentRepository = FirebaseForumCommentRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideCfmRepository(
        remoteDataSource: CfmRemoteDataSource
    ): CfmRepository = CfmRepositoryImpl(remoteDataSource)
}