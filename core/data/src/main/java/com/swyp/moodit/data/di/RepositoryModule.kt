package com.swyp.moodit.data.di

import com.swyp.moodit.data.impl.AuthRepositoryImpl
import com.swyp.moodit.data.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}