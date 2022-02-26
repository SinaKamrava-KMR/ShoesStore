package com.mainway.store.data.repo

import com.mainway.store.data.Product
import com.mainway.store.data.repo.source.ProductDataSource
import com.mainway.store.data.repo.source.ProductLocalDataSource
import com.mainway.store.data.repo.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {


    override fun getProducts(sort: Int): Single<List<Product>> =
        productLocalDataSource.getFavoriteProduct().flatMap { favoriteProducts ->
            remoteDataSource.getProducts(sort).doOnSuccess {
                val favoriteProductsIds = favoriteProducts.map { it.id }
                it.forEach { produst ->
                    if (favoriteProductsIds.contains(produst.id)) {
                        produst.isFavorite = true
                    }
                }
            }
        }


    override fun getFavoriteProduct(): Single<List<Product>> =
        productLocalDataSource.getFavoriteProduct()

    override fun addToFavorite(product: Product): Completable =
        productLocalDataSource.addToFavorite(product)

    override fun deleteFromFavorite(product: Product): Completable =
        productLocalDataSource.deleteFromFavorite(product)


}