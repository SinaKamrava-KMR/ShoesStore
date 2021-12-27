package com.mainway.store.data.repo

import com.mainway.store.data.Comment
import io.reactivex.Single

interface CommentRepository {
    fun getAll(product_id:Int): Single<List<Comment>>
    fun insert() : Single<Comment>
}