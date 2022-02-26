package com.mainway.store.data.repo

import com.mainway.store.data.AddToCartResponse
import com.mainway.store.data.CartItemCount
import com.mainway.store.data.CartResponse
import com.mainway.store.data.MessageResponse
import com.mainway.store.data.repo.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImpl(private val remoteDataSource: CartDataSource) : CartRepository {


    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = remoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> =
        remoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        remoteDataSource.changeCount(cartItemId, count)

    override fun getCartItemsCount(): Single<CartItemCount> = remoteDataSource.getCartItemsCount()


}