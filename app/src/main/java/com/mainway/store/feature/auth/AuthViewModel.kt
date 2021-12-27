package com.mainway.store.feature.auth

import com.mainway.store.common.NikeViewModel
import com.mainway.store.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    fun login(email:String,password:String):Completable{
        progressBarLiveData.value=true
        return userRepository.login(email,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signup(email:String,password:String):Completable{
        progressBarLiveData.value=true
        return userRepository.signUp(email,password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

}