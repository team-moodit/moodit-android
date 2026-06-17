package com.swyp.moodit.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.swyp.moodit.datastore.token.AuthDataStore
import com.swyp.moodit.datastore.token.AuthDataStoreImpl
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context: Context
    ): AuthDataStore {
        val dataStore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("auth_preferences")
        }
        return AuthDataStoreImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): UserPreferencesDataStore {
        val dataStore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("user_preferences")
        }
        return UserPreferencesDataStoreImpl(dataStore)
    }
}