package com.mainway.store.data.repo.source

import com.mainway.store.data.AddToCartResponse
import com.mainway.store.data.CartItemCount
import com.mainway.store.data.CartResponse
import com.mainway.store.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId:Int):Single<AddToCartResponse>
    fun get() :Single<CartResponse>
    fun remove(cartItemId:Int):Single<MessageResponse>
    fun changeCount(cartItemId:Int,count:Int):Single<AddToCartResponse>
    fun getCartItemsCount():Single<CartItemCount>

}