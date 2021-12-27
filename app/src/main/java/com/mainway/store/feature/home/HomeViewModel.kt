package com.mainway.store.feature.home


import androidx.lifecycle.MutableLiveData
import com.mainway.store.common.NikeSingleObserver

import com.mainway.store.data.Product
import com.mainway.store.data.repo.ProductRepository
import com.mainway.store.common.NikeViewModel
import com.mainway.store.data.Banner
import com.mainway.store.data.SORT_LATEST
import com.mainway.store.data.SORT_POPULAR
import com.mainway.store.data.repo.BannerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomeViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val productPopularLiveData = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        //=======================Products==================
        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }
            })

        //=====================Products Popular =================

        productRepository.getProducts(SORT_POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productPopularLiveData.value=t
                }

            })


        //=======================Banner=================

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }

            })
    }

}