package com.mainway.store.data.repo.source

import com.mainway.store.data.MessageResponse
import com.mainway.store.data.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {
    fun login(userName:String,password:String):Single<TokenResponse>
    fun signUp(userName:String,password: String):Single<MessageResponse>
    fun loadToken()
    fun saveToken(token:String,refreshToken:String)
}