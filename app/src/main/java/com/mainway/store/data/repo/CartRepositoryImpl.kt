package com.mainway.store.data.repo

import com.mainway.store.data.AddToCartResponse
import com.mainway.store.data.CartItemCount
import com.mainway.store.data.CartResponse
import com.mainway.store.data.MessageResponse
import com.mainway.store.data.repo.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImpl(val remoteDataSource :CartDataSource) :CartRepository {


    override fun addToCart(productId: Int): Single<AddToCartResponse> =remoteDataSource.addToCart(productId)
    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }


}