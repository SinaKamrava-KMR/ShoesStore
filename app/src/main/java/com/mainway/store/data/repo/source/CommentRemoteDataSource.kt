package com.mainway.store.data.repo.source

import com.mainway.store.data.Comment
import com.mainway.store.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) :CommentDataSource {

    override fun getAll(product_id:Int): Single<List<Comment>> =apiService.getComments(product_id)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}