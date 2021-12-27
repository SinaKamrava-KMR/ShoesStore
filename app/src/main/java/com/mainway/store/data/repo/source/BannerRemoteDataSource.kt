package com.mainway.store.data.repo.source

import com.mainway.store.data.Banner
import com.mainway.store.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDataSource {

    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()

}