package com.mainway.store.data.repo.order

import com.mainway.store.data.Checkout
import com.mainway.store.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {
    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod:String
    ):Single<SubmitOrderResult>


    fun checkout(orderId:Int):Single<Checkout>
}