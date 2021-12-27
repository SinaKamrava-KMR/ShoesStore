package com.mainway.store.data.repo

import com.mainway.store.data.Product
import com.mainway.store.data.repo.source.ProductDataSource
import com.mainway.store.data.repo.source.ProductLocalDataSource
import com.mainway.store.data.repo.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductLocalDataSource):ProductRepository {


    override fun getProducts(sort:Int): Single<List<Product>> =remoteDataSource.getProducts(sort)


    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }

}