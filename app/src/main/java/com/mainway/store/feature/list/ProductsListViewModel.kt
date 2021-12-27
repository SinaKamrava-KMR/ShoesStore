package com.mainway.store.feature.list

import androidx.lifecycle.MutableLiveData
import com.mainway.store.R
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.common.asyncNetworkRequest
import com.mainway.store.data.Product
import com.mainway.store.data.repo.ProductRepository

class ProductsListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitle= arrayOf(R.string.sortLatest,R.string.sortPopular,R.string.sortPriceHighToLow,R.string.sortPriceLowToHigh)

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitle[sort]
    }

    fun getProducts() {

        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
    }

    fun onSelectedSortChangeByUser(sort:Int){

        this.sort=sort
        this.selectedSortTitleLiveData.value=sortTitle[sort]
        getProducts()

    }

}