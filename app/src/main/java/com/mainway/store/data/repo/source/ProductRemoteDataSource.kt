package com.mainway.store.data.repo.source

import com.mainway.store.data.Product
import com.mainway.store.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService):ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }


}