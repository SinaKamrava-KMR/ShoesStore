package com.mainway.store.data.repo.source

import com.google.gson.JsonObject
import com.mainway.store.data.MessageResponse
import com.mainway.store.data.TokenResponse
import com.mainway.store.services.http.ApiService
import io.reactivex.Single
const val CLIENT_ID=2
const val CLIENT_SECRET="kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(private val apiService: ApiService) :UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("username",userName)
            addProperty("password",password)
            addProperty("grant_type","password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        })
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
       return apiService.signup(JsonObject().apply {
           addProperty("email",userName)
           addProperty("password",password)
       })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}