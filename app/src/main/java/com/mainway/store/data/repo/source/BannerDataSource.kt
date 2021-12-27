package com.mainway.store.data.repo.source

import com.mainway.store.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners():Single<List<Banner>>
}