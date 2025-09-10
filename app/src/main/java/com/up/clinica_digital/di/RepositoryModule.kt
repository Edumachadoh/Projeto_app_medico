package com.up.clinica_digital.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.up.clinica_digital.data.repository.*
import com.up.clinica_digital.domain.repository.IAppointmentRepository
import com.up.clinica_digital.domain.repository.IDoctorRepository
import com.up.clinica_digital.domain.repository.IForumCommentRepository
import com.up.clinica_digital.domain.repository.IForumTopicRepository
import com.up.clinica_digital.domain.repository.IPatientRepository
import com.up.clinica_digital.domain.repository.IUserAuthRepository
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
    ): IUserAuthRepository = FirebaseUserAuthRepository(auth)

    @Provides
    @Singleton
    fun provideDoctorRepository(
        firestore: FirebaseFirestore
    ): IDoctorRepository = FirebaseDoctorRepository(firestore)

    @Provides
    @Singleton
    fun providePatientRepository(
        firestore: FirebaseFirestore
    ): IPatientRepository = FirebasePatientRepository(firestore)

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        firestore: FirebaseFirestore
    ): IAppointmentRepository = FirebaseAppointmentRepository(firestore)

    @Provides
    @Singleton
    fun provideForumTopicRepository(
        firestore: FirebaseFirestore
    ): IForumTopicRepository = FirebaseForumTopicRepository(firestore)

    @Provides
    @Singleton
    fun provideForumCommentRepository(
        firestore: FirebaseFirestore
    ): IForumCommentRepository = FirebaseForumCommentRepository(firestore)
}