package com.mainway.store.feature.profile

import com.mainway.store.common.NikeViewModel
import com.mainway.store.data.TokenContainer
import com.mainway.store.data.repo.UserRepository
import com.mainway.store.data.repo.source.UserLocalDataSource

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {
    val userName: String
        get() = userRepository.getUserName()

    val isSignIn: Boolean
        get() = TokenContainer.token != null

    fun signOut(){
        userRepository.signOut()
    }

}