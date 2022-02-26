package com.mainway.store.data.repo

import com.mainway.store.data.TokenContainer
import com.mainway.store.data.TokenResponse
import com.mainway.store.data.repo.source.UserDataSource
import com.mainway.store.data.repo.source.UserLocalDataSource
import com.mainway.store.data.repo.source.UserRemoteDataSource
import io.reactivex.Completable

class UserRepositoryImpl(
    private val userLocalDataSource: UserDataSource,
    private val userRemoteDataSource: UserDataSource
) : UserRepository {

    override fun login(userName: String, password: String): Completable {
        return userRemoteDataSource.login(userName, password).doOnSuccess {
            onSuccessFullLogin(userName,it)
        }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {

        return userRemoteDataSource.signUp(userName, password).flatMap {
            userRemoteDataSource.login(userName, password)
        }.doOnSuccess {
            onSuccessFullLogin(userName,it)
        }.ignoreElement()

    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String {
       return userLocalDataSource.getUserName()
    }

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null,null)
    }

    private fun onSuccessFullLogin(userName:String,tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUserName(userName)
    }
}