package com.up.clinica_digital.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.data.repository.*
import com.up.clinica_digital.domain.repository.AppointmentRepository
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

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        auth: FirebaseAuth
    ): UserAuthRepository = FirebaseUserAuthRepositoryImpl(auth)

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
}