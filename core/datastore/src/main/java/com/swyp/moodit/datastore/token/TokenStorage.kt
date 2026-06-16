package com.swyp.moodit.datastore.token

interface TokenStorage {
    fun getAccessToken(): String?
}