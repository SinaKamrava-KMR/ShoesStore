package com.mainway.store.data.repo.source

import androidx.room.*
import com.mainway.store.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProduct(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorite(product: Product): Completable

    @Delete
    override fun deleteFromFavorite(product: Product): Completable


}