package com.swyp.moodit.datastore.di

import com.swyp.moodit.datastore.token.TokenStorage
import com.swyp.moodit.datastore.token.TokenStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun bindTokenStorage(
        tokenStorageImpl: TokenStorageImpl
    ): TokenStorage
}