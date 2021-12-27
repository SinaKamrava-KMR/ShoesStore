package com.mainway.store.services

import com.mainway.store.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView:NikeImageView,imageUrl:String)
}