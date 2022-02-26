package com.mainway.store.data.repo

import com.mainway.store.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(sort:Int):Single<List<Product>>
    fun getFavoriteProduct():Single<List<Product>>
    fun addToFavorite(product: Product):Completable
    fun deleteFromFavorite(product: Product):Completable

}