package com.mainway.store.data.repo.source

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.mainway.store.data.MessageResponse
import com.mainway.store.data.TokenContainer
import com.mainway.store.data.TokenResponse
import io.reactivex.Single

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) :UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString("access_token", null),
            sharedPreferences.getString("refresh_token", null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token",token)
            putString("refresh_token",refreshToken)
        }.apply()
    }

    override fun saveUserName(userName: String) {
        sharedPreferences.edit().apply {
            putString("username",userName)
        }.apply()
    }

    override fun getUserName(): String {
     return sharedPreferences.getString("username","")?:""
    }

    @SuppressLint("CommitPrefEdits")
    override fun signOut() {
        sharedPreferences.edit().apply {
            clear()
        }.apply()
    }
}