package com.mainway.store.data.repo.source

import com.mainway.store.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(product_id:Int): Single<List<Comment>>
    fun insert() : Single<Comment>
}