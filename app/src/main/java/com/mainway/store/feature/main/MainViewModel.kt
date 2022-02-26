package com.mainway.store.feature.main

import com.mainway.store.common.NikeCompletableObserve
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.data.CartItemCount
import com.mainway.store.data.TokenContainer
import com.mainway.store.data.repo.CartRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) :NikeViewModel() {
        fun getCartItemsCount(){
            if(!TokenContainer.token.isNullOrEmpty()){
                cartRepository.getCartItemsCount()
                    .subscribeOn(Schedulers.io())
                    .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                        override fun onSuccess(t: CartItemCount) {
                            EventBus.getDefault().postSticky(t)
                        }

                    })
            }
        }
}