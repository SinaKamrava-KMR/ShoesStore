package com.mainway.store.data.repo.source

import com.mainway.store.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {
    fun getProducts(sort:Int): Single<List<Product>>
    fun getFavoriteProduct(): Single<List<Product>>
    fun addToFavorite(): Completable
    fun deleteFromFavorite(): Completable
}