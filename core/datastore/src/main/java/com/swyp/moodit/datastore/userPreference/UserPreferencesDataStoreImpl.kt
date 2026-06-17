package com.swyp.moodit.datastore.userPreference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class UserPreferencesDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesDataStore {

}