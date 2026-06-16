package com.swyp.moodit.datastore.token

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorageImpl @Inject constructor() : TokenStorage {
    override fun getAccessToken(): String? {
        val testAccessToken = "testAccessToken"
        return testAccessToken
    }
}