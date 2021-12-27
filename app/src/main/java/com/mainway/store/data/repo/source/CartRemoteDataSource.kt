package com.mainway.store.data.repo.source

import com.google.gson.JsonObject
import com.mainway.store.data.AddToCartResponse
import com.mainway.store.data.CartItemCount
import com.mainway.store.data.CartResponse
import com.mainway.store.data.MessageResponse
import com.mainway.store.services.http.ApiService
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) :CartDataSource{

    override fun addToCart(productId: Int): Single<AddToCartResponse> =apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productId)
        }
    )

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