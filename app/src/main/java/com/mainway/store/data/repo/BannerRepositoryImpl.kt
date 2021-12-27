package com.mainway.store.data.repo

import com.mainway.store.data.Banner
import com.mainway.store.data.repo.source.BannerDataSource
import com.mainway.store.data.repo.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(private val bannerRemoteDataSource: BannerDataSource) :BannerRepository {

    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}