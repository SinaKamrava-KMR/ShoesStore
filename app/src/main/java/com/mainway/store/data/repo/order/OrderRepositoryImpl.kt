package com.mainway.store.data.repo.order

import com.mainway.store.data.Checkout
import com.mainway.store.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    override fun checkout(orderId: Int): Single<Checkout> {
       return orderDataSource.checkout(orderId)
    }
}