package com.mainway.store.data.repo

import com.mainway.store.data.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanners():Single<List<Banner>>
}