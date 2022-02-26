package com.mainway.store.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.common.asyncNetworkRequest
import com.mainway.store.data.Checkout
import com.mainway.store.data.repo.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {
    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }

            })
    }
}