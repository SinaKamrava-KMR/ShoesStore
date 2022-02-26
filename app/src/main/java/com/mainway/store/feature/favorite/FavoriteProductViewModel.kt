package com.mainway.store.feature.favorite

import androidx.lifecycle.MutableLiveData
import com.mainway.store.common.NikeCompletableObserve
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.data.Product
import com.mainway.store.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductViewModel(private val productRepository: ProductRepository) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProduct()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.postValue(t)
                }

            })

    }

    fun removerFroFavorite(product: Product) {
        productRepository.deleteFromFavorite(product).subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserve(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("remove From Favorite Completed ")
                }

            })
    }

}